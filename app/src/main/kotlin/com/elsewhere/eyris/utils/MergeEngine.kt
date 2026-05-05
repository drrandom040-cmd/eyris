package com.elsewhere.eyris.utils

import com.elsewhere.eyris.domain.model.Business
import kotlin.math.pow
import kotlin.math.sqrt

object MergeEngine {
    private const val DISTANCE_THRESHOLD_METERS = 50.0
    private const val NAME_SIMILARITY_THRESHOLD = 0.85

    /**
     * Merges businesses from multiple sources and deduplicates them.
     * Uses fuzzy name matching and geographic distance threshold.
     */
    fun mergeBusiness(
        googleMapsBusiness: List<Business>,
        foursquareBusiness: List<Business>,
        osmBusiness: List<Business>
    ): List<Business> {
        val allBusinesses = googleMapsBusiness + foursquareBusiness + osmBusiness
        val merged = mutableListOf<Business>()
        val processed = mutableSetOf<String>()

        for (business in allBusinesses) {
            if (business.id in processed) continue

            // Find duplicates
            val duplicates = allBusinesses.filter { other ->
                other.id !in processed &&
                        isSameBusiness(business, other)
            }

            // Merge duplicates and take the best one
            val mergedBusiness = mergeDuplicates(duplicates)
            merged.add(mergedBusiness)

            // Mark all as processed
            duplicates.forEach { processed.add(it.id) }
        }

        return merged
    }

    /**
     * Checks if two businesses are the same using fuzzy name matching and distance.
     */
    private fun isSameBusiness(business1: Business, business2: Business): Boolean {
        // Check distance
        val distance = calculateDistance(
            business1.latitude, business1.longitude,
            business2.latitude, business2.longitude
        )

        if (distance > DISTANCE_THRESHOLD_METERS) return false

        // Check name similarity
        val similarity = calculateStringSimilarity(business1.name, business2.name)
        return similarity >= NAME_SIMILARITY_THRESHOLD
    }

    /**
     * Merges duplicate businesses by selecting the best data from each source.
     */
    private fun mergeDuplicates(businesses: List<Business>): Business {
        if (businesses.isEmpty()) throw IllegalArgumentException("Cannot merge empty list")
        if (businesses.size == 1) return businesses.first()

        // Start with the first business
        var merged = businesses.first()

        // Fill in missing data from other sources
        for (business in businesses.drop(1)) {
            merged = merged.copy(
                phone = merged.phone ?: business.phone,
                email = merged.email ?: business.email,
                website = merged.website ?: business.website,
                hasWebsite = merged.hasWebsite || business.hasWebsite,
                rating = if (business.reviewCount > (merged.reviewCount ?: 0)) business.rating else merged.rating,
                reviewCount = maxOf(merged.reviewCount, business.reviewCount),
                openingHours = merged.openingHours ?: business.openingHours,
                coverImageUrl = merged.coverImageUrl ?: business.coverImageUrl,
                instagram = merged.instagram ?: business.instagram,
                facebook = merged.facebook ?: business.facebook,
                tiktok = merged.tiktok ?: business.tiktok,
                whatsapp = merged.whatsapp ?: business.whatsapp
            )
        }

        return merged
    }

    /**
     * Calculates distance between two coordinates using Haversine formula.
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371000.0 // Earth radius in meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2).pow(2)
        val c = 2 * Math.asin(sqrt(a))
        return R * c
    }

    /**
     * Calculates string similarity using Levenshtein distance.
     */
    private fun calculateStringSimilarity(str1: String, str2: String): Double {
        val s1 = str1.lowercase()
        val s2 = str2.lowercase()

        val distance = levenshteinDistance(s1, s2)
        val maxLength = maxOf(s1.length, s2.length)

        return if (maxLength == 0) 1.0 else 1.0 - (distance.toDouble() / maxLength)
    }

    /**
     * Calculates Levenshtein distance between two strings.
     */
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // deletion
                    dp[i][j - 1] + 1,      // insertion
                    dp[i - 1][j - 1] + cost // substitution
                )
            }
        }

        return dp[s1.length][s2.length]
    }
}
