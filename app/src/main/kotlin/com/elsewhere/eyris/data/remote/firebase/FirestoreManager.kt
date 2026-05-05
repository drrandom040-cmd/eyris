package com.elsewhere.eyris.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.elsewhere.eyris.data.local.room.entity.BusinessEntity
import com.elsewhere.eyris.data.local.room.entity.ContactedBusinessEntity
import com.elsewhere.eyris.data.local.room.entity.UserEntity
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreManager @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    // User operations
    suspend fun saveUser(user: UserEntity): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(user.userId)
                .set(user, SetOptions.merge())
                .await()
            Timber.d("User saved: ${user.userId}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving user")
            Result.failure(e)
        }
    }

    suspend fun getUser(userId: String): Result<UserEntity?> {
        return try {
            val document = firestore.collection("users")
                .document(userId)
                .get()
                .await()
            
            val user = document.toObject(UserEntity::class.java)
            Result.success(user)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching user")
            Result.failure(e)
        }
    }

    // Lead operations
    suspend fun saveLead(lead: BusinessEntity): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(lead.userId)
                .collection("leads")
                .document(lead.id)
                .set(lead, SetOptions.merge())
                .await()
            Timber.d("Lead saved: ${lead.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving lead")
            Result.failure(e)
        }
    }

    suspend fun saveLeads(userId: String, leads: List<BusinessEntity>): Result<Unit> {
        return try {
            val batch = firestore.batch()
            leads.forEach { lead ->
                val docRef = firestore.collection("users")
                    .document(userId)
                    .collection("leads")
                    .document(lead.id)
                batch.set(docRef, lead, SetOptions.merge())
            }
            batch.commit().await()
            Timber.d("Batch saved ${leads.size} leads")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving leads batch")
            Result.failure(e)
        }
    }

    suspend fun deleteLead(userId: String, leadId: String): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("leads")
                .document(leadId)
                .delete()
                .await()
            Timber.d("Lead deleted: $leadId")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting lead")
            Result.failure(e)
        }
    }

    suspend fun getLeads(userId: String): Result<List<BusinessEntity>> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("leads")
                .get()
                .await()
            
            val leads = snapshot.toObjects(BusinessEntity::class.java)
            Result.success(leads)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching leads")
            Result.failure(e)
        }
    }

    // Contacted business operations
    suspend fun saveContactedBusiness(contacted: ContactedBusinessEntity): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(contacted.userId)
                .collection("contacted")
                .document(contacted.id)
                .set(contacted, SetOptions.merge())
                .await()
            Timber.d("Contacted business saved: ${contacted.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving contacted business")
            Result.failure(e)
        }
    }

    suspend fun saveContactedBusinesses(
        userId: String,
        contacted: List<ContactedBusinessEntity>
    ): Result<Unit> {
        return try {
            val batch = firestore.batch()
            contacted.forEach { business ->
                val docRef = firestore.collection("users")
                    .document(userId)
                    .collection("contacted")
                    .document(business.id)
                batch.set(docRef, business, SetOptions.merge())
            }
            batch.commit().await()
            Timber.d("Batch saved ${contacted.size} contacted businesses")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving contacted businesses batch")
            Result.failure(e)
        }
    }

    suspend fun deleteContactedBusiness(userId: String, contactedId: String): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("contacted")
                .document(contactedId)
                .delete()
                .await()
            Timber.d("Contacted business deleted: $contactedId")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting contacted business")
            Result.failure(e)
        }
    }

    suspend fun getContactedBusinesses(userId: String): Result<List<ContactedBusinessEntity>> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("contacted")
                .get()
                .await()
            
            val contacted = snapshot.toObjects(ContactedBusinessEntity::class.java)
            Result.success(contacted)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching contacted businesses")
            Result.failure(e)
        }
    }

    suspend fun getContactedBusinessesByStatus(
        userId: String,
        status: String
    ): Result<List<ContactedBusinessEntity>> {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("contacted")
                .whereEqualTo("status", status)
                .get()
                .await()
            
            val contacted = snapshot.toObjects(ContactedBusinessEntity::class.java)
            Result.success(contacted)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching contacted businesses by status")
            Result.failure(e)
        }
    }

    // Search history
    suspend fun saveSearchHistory(
        userId: String,
        query: String,
        location: String,
        category: String,
        resultCount: Int
    ): Result<Unit> {
        return try {
            val historyId = firestore.collection("users")
                .document(userId)
                .collection("search_history")
                .document().id

            val historyData = mapOf(
                "id" to historyId,
                "query" to query,
                "location" to location,
                "category" to category,
                "resultCount" to resultCount,
                "timestamp" to System.currentTimeMillis()
            )

            firestore.collection("users")
                .document(userId)
                .collection("search_history")
                .document(historyId)
                .set(historyData)
                .await()

            Timber.d("Search history saved")
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error saving search history")
            Result.failure(e)
        }
    }
}
