package com.elsewhere.eyris.data.remote.google

import com.elsewhere.eyris.domain.model.Business
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import org.jsoup.Jsoup
import timber.log.Timber

class GoogleMapsScraper(private val httpClient: HttpClient) {

    /**
     * Scrapes Google Maps for businesses.
     * Note: This is a simplified implementation. Full scraping would require:
     * - Handling JavaScript rendering
     * - Managing rate limiting
     * - Handling CAPTCHA challenges
     * 
     * For production, consider using a backend service or Google Maps API.
     */
    suspend fun searchBusinesses(
        query: String,
        location: String,
        latitude: Double,
        longitude: Double
    ): List<Business> {
        return try {
            // Build search URL
            val searchUrl = buildGoogleMapsUrl(query, location, latitude, longitude)
            
            Timber.d("Scraping Google Maps: $searchUrl")

            // Fetch page with mobile User-Agent
            val html = httpClient.get(searchUrl) {
                header("User-Agent", MOBILE_USER_AGENT)
                header("Accept-Language", "en-US,en;q=0.9")
            }.body<String>()

            // Parse HTML and extract businesses
            parseBusinessesFromHtml(html, query)
        } catch (e: Exception) {
            Timber.e(e, "Error scraping Google Maps")
            emptyList()
        }
    }

    private fun buildGoogleMapsUrl(
        query: String,
        location: String,
        latitude: Double,
        longitude: Double
    ): String {
        val searchQuery = "$query $location".replace(" ", "+")
        return "https://www.google.com/maps/search/$searchQuery/@$latitude,$longitude,15z"
    }

    private fun parseBusinessesFromHtml(html: String, query: String): List<Business> {
        return try {
            val doc = Jsoup.parse(html)
            val businesses = mutableListOf<Business>()

            // Note: Google Maps structure changes frequently
            // This is a simplified extraction pattern
            doc.select("[data-item-id]").forEach { element ->
                try {
                    val name = element.select(".fontHeadlineSmall").text()
                    val rating = element.select(".icon-star").text().toDoubleOrNull()
                    val address = element.select(".fontBodySmall").text()
                    
                    if (name.isNotEmpty()) {
                        val business = Business(
                            id = "gm_${System.nanoTime()}",
                            name = name,
                            category = query,
                            address = address,
                            latitude = 0.0,
                            longitude = 0.0,
                            rating = rating,
                            source = "google_maps"
                        )
                        businesses.add(business)
                    }
                } catch (e: Exception) {
                    Timber.e(e, "Error parsing business element")
                }
            }

            Timber.d("Parsed ${businesses.size} businesses from Google Maps")
            businesses
        } catch (e: Exception) {
            Timber.e(e, "Error parsing HTML")
            emptyList()
        }
    }

    companion object {
        private const val MOBILE_USER_AGENT = 
            "Mozilla/5.0 (Linux; Android 13; SM-G991B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
    }
}
