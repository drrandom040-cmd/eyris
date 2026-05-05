package com.elsewhere.eyris.data.repository

import com.elsewhere.eyris.data.local.room.dao.BusinessDao
import com.elsewhere.eyris.data.local.room.entity.BusinessEntity
import com.elsewhere.eyris.data.local.room.entity.ContactedBusinessEntity
import com.elsewhere.eyris.data.remote.firebase.FirebaseAuthManager
import com.elsewhere.eyris.data.remote.firebase.FirestoreManager
import com.elsewhere.eyris.data.remote.foursquare.FoursquareClient
import com.elsewhere.eyris.data.remote.google.GoogleMapsScraper
import com.elsewhere.eyris.data.remote.osm.OsmClient
import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.domain.model.SearchResult
import com.elsewhere.eyris.domain.repository.BusinessRepository
import com.elsewhere.eyris.utils.MergeEngine
import com.elsewhere.eyris.utils.RankingEngine
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
    private val businessDao: BusinessDao,
    private val foursquareClient: FoursquareClient,
    private val osmClient: OsmClient,
    private val googleMapsScraper: GoogleMapsScraper,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val firestoreManager: FirestoreManager
) : BusinessRepository {

    override suspend fun searchBusinesses(
        query: String,
        location: String,
        latitude: Double,
        longitude: Double
    ): Result<SearchResult> {
        return try {
            Timber.d("Searching businesses: $query at $location")

            // Fire parallel requests to all three sources
            val results = coroutineScope {
                val foursquareDeferred = async {
                    foursquareClient.searchPlaces(query, latitude, longitude)
                }
                val osmDeferred = async {
                    osmClient.searchBusinesses(latitude, longitude, query = query)
                }
                val googleDeferred = async {
                    googleMapsScraper.searchBusinesses(query, location, latitude, longitude)
                }

                awaitAll(foursquareDeferred, osmDeferred, googleDeferred)
            }

            val foursquareResults = results[0]
            val osmResults = results[1]
            val googleResults = results[2]

            Timber.d("Got ${foursquareResults.size} from Foursquare, ${osmResults.size} from OSM, ${googleResults.size} from Google")

            // Merge and deduplicate
            val merged = MergeEngine.mergeBusiness(googleResults, foursquareResults, osmResults)

            // Rank businesses
            val ranked = RankingEngine.rankBusinesses(merged)

            // Filter and cap to 20
            val filtered = RankingEngine.filterAndCapResults(ranked, maxResults = 20)

            // Get existing leads and contacted businesses to exclude them
            val existingLeadIds = businessDao.getLeads().map { it.id }.toSet()
            val existingContactedIds = businessDao.getContactedBusinessesByUser(
                firebaseAuthManager.getCurrentUserId() ?: ""
            ).map { it.businessId }.toSet()

            val finalResults = filtered.filter { 
                it.id !in existingLeadIds && it.id !in existingContactedIds
            }

            // Save to local database
            val userId = firebaseAuthManager.getCurrentUserId() ?: ""
            val businessEntities = finalResults.map { business ->
                BusinessEntity(
                    id = business.id,
                    userId = userId,
                    name = business.name,
                    category = business.category,
                    address = business.address,
                    latitude = business.latitude,
                    longitude = business.longitude,
                    phone = business.phone,
                    email = business.email,
                    website = business.website,
                    hasWebsite = business.hasWebsite,
                    rating = business.rating,
                    reviewCount = business.reviewCount,
                    weightedScore = business.weightedScore,
                    openingHours = business.openingHours,
                    coverImageUrl = business.coverImageUrl,
                    instagram = business.instagram,
                    facebook = business.facebook,
                    tiktok = business.tiktok,
                    whatsapp = business.whatsapp,
                    source = business.source,
                    savedAt = System.currentTimeMillis(),
                    searchQuery = query,
                    synced = false
                )
            }

            businessDao.insertBusinesses(businessEntities)

            // Save search history
            saveSearchHistory(query, location, "General", finalResults.size)

            val searchResult = SearchResult(
                businesses = finalResults,
                totalCount = finalResults.size,
                query = query
            )

            Result.success(searchResult)
        } catch (e: Exception) {
            Timber.e(e, "Error searching businesses")
            Result.failure(e)
        }
    }

    override suspend fun saveLead(business: Business): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            val entity = BusinessEntity(
                id = business.id,
                userId = userId,
                name = business.name,
                category = business.category,
                address = business.address,
                latitude = business.latitude,
                longitude = business.longitude,
                phone = business.phone,
                email = business.email,
                website = business.website,
                hasWebsite = business.hasWebsite,
                rating = business.rating,
                reviewCount = business.reviewCount,
                weightedScore = business.weightedScore,
                openingHours = business.openingHours,
                coverImageUrl = business.coverImageUrl,
                instagram = business.instagram,
                facebook = business.facebook,
                tiktok = business.tiktok,
                whatsapp = business.whatsapp,
                source = business.source,
                savedAt = System.currentTimeMillis(),
                synced = false
            )

            businessDao.insertBusiness(entity)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving lead")
            Result.failure(e)
        }
    }

    override suspend fun saveLeads(businesses: List<Business>): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            val entities = businesses.map { business ->
                BusinessEntity(
                    id = business.id,
                    userId = userId,
                    name = business.name,
                    category = business.category,
                    address = business.address,
                    latitude = business.latitude,
                    longitude = business.longitude,
                    phone = business.phone,
                    email = business.email,
                    website = business.website,
                    hasWebsite = business.hasWebsite,
                    rating = business.rating,
                    reviewCount = business.reviewCount,
                    weightedScore = business.weightedScore,
                    openingHours = business.openingHours,
                    coverImageUrl = business.coverImageUrl,
                    instagram = business.instagram,
                    facebook = business.facebook,
                    tiktok = business.tiktok,
                    whatsapp = business.whatsapp,
                    source = business.source,
                    savedAt = System.currentTimeMillis(),
                    synced = false
                )
            }

            businessDao.insertBusinesses(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving leads")
            Result.failure(e)
        }
    }

    override suspend fun deleteLead(businessId: String): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            businessDao.deleteBusinessByIdAndUser(userId, businessId)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting lead")
            Result.failure(e)
        }
    }

    override fun getLeads(): Flow<List<Business>> {
        val userId = firebaseAuthManager.getCurrentUserId() ?: ""
        return businessDao.getLeads(userId).map { entities ->
            entities.map { it.toBusinessModel() }
        }
    }

    override suspend fun getLeadById(id: String): Business? {
        return businessDao.getBusinessById(id)?.toBusinessModel()
    }

    override suspend fun moveToContacted(
        business: Business,
        status: ContactStatus,
        notes: String?,
        socialHandle: String?
    ): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            val entity = ContactedBusinessEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                businessId = business.id,
                name = business.name,
                category = business.category,
                address = business.address,
                latitude = business.latitude,
                longitude = business.longitude,
                phone = business.phone,
                email = business.email,
                website = business.website,
                hasWebsite = business.hasWebsite,
                rating = business.rating,
                reviewCount = business.reviewCount,
                weightedScore = business.weightedScore,
                openingHours = business.openingHours,
                coverImageUrl = business.coverImageUrl,
                instagram = business.instagram,
                facebook = business.facebook,
                tiktok = business.tiktok,
                whatsapp = business.whatsapp,
                source = business.source,
                status = status.name,
                notes = notes,
                socialHandleTapped = socialHandle,
                contactedAt = System.currentTimeMillis(),
                lastUpdatedAt = System.currentTimeMillis(),
                synced = false
            )

            businessDao.insertContactedBusiness(entity)
            businessDao.deleteBusinessByIdAndUser(userId, business.id)

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error moving to contacted")
            Result.failure(e)
        }
    }

    override suspend fun updateContactStatus(
        businessId: String,
        status: ContactStatus,
        notes: String?
    ): Result<Unit> {
        return try {
            val contacted = businessDao.getContactedBusinessById(businessId)
                ?: return Result.failure(Exception("Contacted business not found"))

            val updated = contacted.copy(
                status = status.name,
                notes = notes ?: contacted.notes,
                lastUpdatedAt = System.currentTimeMillis(),
                synced = false
            )

            businessDao.updateContactedBusiness(updated)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error updating contact status")
            Result.failure(e)
        }
    }

    override suspend fun deleteContacted(businessId: String): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            businessDao.deleteContactedBusinessByIdAndUser(userId, businessId)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting contacted")
            Result.failure(e)
        }
    }

    override fun getContactedBusinesses(): Flow<List<Business>> {
        val userId = firebaseAuthManager.getCurrentUserId() ?: ""
        return businessDao.getContactedBusinessesByUser(userId).map { entities ->
            entities.map { it.toBusinessModel() }
        }
    }

    override fun getContactedBusinessesByStatus(status: ContactStatus): Flow<List<Business>> {
        val userId = firebaseAuthManager.getCurrentUserId() ?: ""
        return businessDao.getContactedBusinessesByStatus(userId, status.name).map { entities ->
            entities.map { it.toBusinessModel() }
        }
    }

    override suspend fun getContactedBusinessById(id: String): Business? {
        return businessDao.getContactedBusinessById(id)?.toBusinessModel()
    }

    override suspend fun syncWithFirebase(): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            // Sync leads
            val leads = businessDao.getLeads(userId).map { entities ->
                entities.map { it.toBusinessModel() }
            }

            // Sync contacted businesses
            val contacted = businessDao.getContactedBusinessesByUser(userId).map { entities ->
                entities.map { it.toBusinessModel() }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error syncing with Firebase")
            Result.failure(e)
        }
    }

    override suspend fun saveSearchHistory(
        query: String,
        location: String,
        category: String,
        resultCount: Int
    ): Result<Unit> {
        return try {
            val userId = firebaseAuthManager.getCurrentUserId() ?: return Result.failure(
                Exception("User not authenticated")
            )

            firestoreManager.saveSearchHistory(userId, query, location, category, resultCount)
        } catch (e: Exception) {
            Timber.e(e, "Error saving search history")
            Result.failure(e)
        }
    }

    private fun BusinessEntity.toBusinessModel(): Business {
        return Business(
            id = id,
            name = name,
            category = category,
            address = address,
            latitude = latitude,
            longitude = longitude,
            phone = phone,
            email = email,
            website = website,
            hasWebsite = hasWebsite,
            rating = rating,
            reviewCount = reviewCount,
            weightedScore = weightedScore,
            openingHours = openingHours,
            coverImageUrl = coverImageUrl,
            instagram = instagram,
            facebook = facebook,
            tiktok = tiktok,
            whatsapp = whatsapp,
            source = source,
            savedAt = savedAt,
            searchQuery = searchQuery,
            synced = synced
        )
    }

    private fun ContactedBusinessEntity.toBusinessModel(): Business {
        return Business(
            id = businessId,
            name = name,
            category = category,
            address = address,
            latitude = latitude,
            longitude = longitude,
            phone = phone,
            email = email,
            website = website,
            hasWebsite = hasWebsite,
            rating = rating,
            reviewCount = reviewCount,
            weightedScore = weightedScore,
            openingHours = openingHours,
            coverImageUrl = coverImageUrl,
            instagram = instagram,
            facebook = facebook,
            tiktok = tiktok,
            whatsapp = whatsapp,
            source = source,
            savedAt = contactedAt,
            synced = synced
        )
    }
}
