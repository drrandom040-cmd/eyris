package com.elsewhere.eyris.data.remote.osm

import com.elsewhere.eyris.domain.model.Business
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import timber.log.Timber

@Serializable
data class OsmOverpassResponse(
    val elements: List<OsmElement>
)

@Serializable
data class OsmElement(
    val type: String,
    val id: Long,
    val lat: Double? = null,
    val lon: Double? = null,
    val tags: Map<String, String>? = null,
    val center: OsmCenter? = null
)

@Serializable
data class OsmCenter(
    val lat: Double,
    val lon: Double
)

class OsmClient(private val httpClient: HttpClient) {
    private val baseUrl = "https://overpass-api.de/api/interpreter"

    suspend fun searchBusinesses(
        latitude: Double,
        longitude: Double,
        radiusMeters: Int = 1000,
        query: String = ""
    ): List<Business> {
        return try {
            // Build Overpass query
            val bbox = calculateBbox(latitude, longitude, radiusMeters)
            val overpassQuery = buildOverpassQuery(bbox, query)

            val response = httpClient.get(baseUrl) {
                url {
                    parameters.append("data", overpassQuery)
                }
            }.body<OsmOverpassResponse>()

            response.elements.mapNotNull { element ->
                try {
                    convertToBusinessModel(element)
                } catch (e: Exception) {
                    Timber.e(e, "Error converting OSM element")
                    null
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error searching OSM places")
            emptyList()
        }
    }

    private fun buildOverpassQuery(bbox: String, query: String): String {
        val amenities = listOf(
            "shop", "restaurant", "cafe", "bar", "office", "studio",
            "salon", "gym", "clinic", "pharmacy", "school", "hotel"
        )

        val amenityFilter = amenities.joinToString("|") { "\"$it\"" }

        return """
            [bbox:$bbox];
            (
              node["amenity"~$amenityFilter];
              way["amenity"~$amenityFilter];
              relation["amenity"~$amenityFilter];
            );
            out center;
        """.trimIndent()
    }

    private fun calculateBbox(latitude: Double, longitude: Double, radiusMeters: Int): String {
        // Approximate conversion: 1 degree ≈ 111 km
        val latDelta = radiusMeters / 111000.0
        val lonDelta = radiusMeters / (111000.0 * Math.cos(Math.toRadians(latitude)))

        val minLat = latitude - latDelta
        val maxLat = latitude + latDelta
        val minLon = longitude - lonDelta
        val maxLon = longitude + lonDelta

        return "$minLat,$minLon,$maxLat,$maxLon"
    }

    private fun convertToBusinessModel(element: OsmElement): Business? {
        val tags = element.tags ?: return null
        val lat = element.lat ?: element.center?.lat ?: return null
        val lon = element.lon ?: element.center?.lon ?: return null

        val name = tags["name"] ?: return null
        val amenity = tags["amenity"] ?: "Unknown"
        val address = buildAddress(tags)

        return Business(
            id = "osm_${element.type}_${element.id}",
            name = name,
            category = amenity,
            address = address,
            latitude = lat,
            longitude = lon,
            phone = tags["phone"],
            email = tags["email"],
            website = tags["website"] ?: tags["url"],
            hasWebsite = !tags["website"].isNullOrEmpty() || !tags["url"].isNullOrEmpty(),
            rating = tags["rating"]?.toDoubleOrNull(),
            openingHours = tags["opening_hours"],
            instagram = tags["contact:instagram"],
            facebook = tags["contact:facebook"],
            tiktok = tags["contact:tiktok"],
            whatsapp = tags["contact:whatsapp"],
            source = "osm"
        )
    }

    private fun buildAddress(tags: Map<String, String>): String {
        val parts = mutableListOf<String>()

        tags["addr:street"]?.let { parts.add(it) }
        tags["addr:housenumber"]?.let { parts.add(it) }
        tags["addr:city"]?.let { parts.add(it) }
        tags["addr:postcode"]?.let { parts.add(it) }

        return if (parts.isNotEmpty()) parts.joinToString(", ") else "Unknown Address"
    }
}
