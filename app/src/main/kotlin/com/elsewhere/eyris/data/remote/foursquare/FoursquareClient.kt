package com.elsewhere.eyris.data.remote.foursquare

import com.elsewhere.eyris.BuildConfig
import com.elsewhere.eyris.domain.model.Business
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.serialization.Serializable
import timber.log.Timber

@Serializable
data class FoursquarePlacesResponse(
    val results: List<FoursquarePlace>
)

@Serializable
data class FoursquarePlace(
    val fsq_id: String,
    val name: String,
    val location: FoursquareLocation? = null,
    val categories: List<FoursquareCategory>? = null,
    val tel: String? = null,
    val website: String? = null,
    val hours: FoursquareHours? = null,
    val rating: Double? = null,
    val stats: FoursquareStats? = null,
    val photos: List<FoursquarePhoto>? = null,
    val social_media: List<FoursquareSocialMedia>? = null
)

@Serializable
data class FoursquareLocation(
    val address: String? = null,
    val country: String? = null,
    val cross_street: String? = null,
    val dma: String? = null,
    val formatted_address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locality: String? = null,
    val postcode: String? = null,
    val region: String? = null
)

@Serializable
data class FoursquareCategory(
    val id: String,
    val name: String,
    val icon: FoursquareIcon? = null,
    val short_name: String? = null
)

@Serializable
data class FoursquareIcon(
    val prefix: String,
    val suffix: String
)

@Serializable
data class FoursquareHours(
    val display: String? = null,
    val is_open: Boolean? = null,
    val open_now: Boolean? = null,
    val regular: List<String>? = null
)

@Serializable
data class FoursquareStats(
    val total_photos: Int? = null,
    val total_tips: Int? = null,
    val total_visits: Int? = null
)

@Serializable
data class FoursquarePhoto(
    val id: String,
    val created_at: String? = null,
    val prefix: String? = null,
    val suffix: String? = null,
    val tip: String? = null,
    val user: FoursquareUser? = null
)

@Serializable
data class FoursquareUser(
    val id: String,
    val first_name: String? = null,
    val last_name: String? = null,
    val photo: FoursquareUserPhoto? = null
)

@Serializable
data class FoursquareUserPhoto(
    val prefix: String,
    val suffix: String
)

@Serializable
data class FoursquareSocialMedia(
    val type: String,
    val url: String? = null
)

class FoursquareClient(private val httpClient: HttpClient) {
    private val apiKey = BuildConfig.FSQ_API_KEY
    private val baseUrl = "https://api.foursquare.com/v3/places/search"

    suspend fun searchPlaces(
        query: String,
        latitude: Double,
        longitude: Double,
        limit: Int = 50
    ): List<Business> {
        return try {
            val response = httpClient.get(baseUrl) {
                header("Authorization", apiKey)
                url {
                    parameters.append("query", query)
                    parameters.append("ll", "$latitude,$longitude")
                    parameters.append("limit", limit.toString())
                    parameters.append("fields", "fsq_id,name,location,categories,tel,website,hours,rating,stats,photos,social_media")
                }
            }.body<FoursquarePlacesResponse>()

            response.results.mapNotNull { place ->
                try {
                    convertToBusinessModel(place)
                } catch (e: Exception) {
                    Timber.e(e, "Error converting Foursquare place: ${place.name}")
                    null
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error searching Foursquare places")
            emptyList()
        }
    }

    private fun convertToBusinessModel(place: FoursquarePlace): Business {
        val location = place.location
        val category = place.categories?.firstOrNull()?.name ?: "Unknown"
        val coverImageUrl = place.photos?.firstOrNull()?.let { photo ->
            "${photo.prefix}300x300${photo.suffix}"
        }

        // Extract social media handles
        var instagram: String? = null
        var facebook: String? = null
        var tiktok: String? = null
        var whatsapp: String? = null

        place.social_media?.forEach { social ->
            when (social.type.lowercase()) {
                "instagram" -> instagram = social.url
                "facebook" -> facebook = social.url
                "tiktok" -> tiktok = social.url
                "whatsapp" -> whatsapp = social.url
            }
        }

        return Business(
            id = "fsq_${place.fsq_id}",
            name = place.name,
            category = category,
            address = location?.formatted_address ?: "${location?.locality}, ${location?.region}",
            latitude = location?.latitude ?: 0.0,
            longitude = location?.longitude ?: 0.0,
            phone = place.tel,
            website = place.website,
            hasWebsite = !place.website.isNullOrEmpty(),
            rating = place.rating,
            reviewCount = place.stats?.total_visits ?: 0,
            openingHours = place.hours?.display,
            coverImageUrl = coverImageUrl,
            instagram = instagram,
            facebook = facebook,
            tiktok = tiktok,
            whatsapp = whatsapp,
            source = "foursquare"
        )
    }
}
