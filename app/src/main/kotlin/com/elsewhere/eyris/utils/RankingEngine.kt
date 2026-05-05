package com.elsewhere.eyris.utils

import com.elsewhere.eyris.domain.model.Business

object RankingEngine {
    private const val GLOBAL_AVERAGE_RATING = 3.5
    private const val REVIEW_WEIGHT_FACTOR = 10

    /**
     * Ranks businesses using Bayesian Average formula.
     * Formula: Score = (reviews / (reviews + 10)) * rating + (10 / (reviews + 10)) * 3.5
     *
     * This ensures businesses with few reviews are pulled toward the global average,
     * preventing outliers from dominating the ranking.
     */
    fun rankBusinesses(businesses: List<Business>): List<Business> {
        return businesses
            .map { business ->
                val weightedScore = calculateBayesianAverage(
                    business.rating ?: GLOBAL_AVERAGE_RATING,
                    business.reviewCount
                )
                business.copy(weightedScore = weightedScore)
            }
            .sortedByDescending { it.weightedScore }
    }

    /**
     * Calculates Bayesian Average score for a business.
     */
    private fun calculateBayesianAverage(rating: Double, reviewCount: Int): Double {
        val reviewWeight = reviewCount.toDouble() / (reviewCount + REVIEW_WEIGHT_FACTOR)
        val globalWeight = REVIEW_WEIGHT_FACTOR.toDouble() / (reviewCount + REVIEW_WEIGHT_FACTOR)

        return (reviewWeight * rating) + (globalWeight * GLOBAL_AVERAGE_RATING)
    }

    /**
     * Filters businesses without websites and caps the result to a maximum count.
     */
    fun filterAndCapResults(
        businesses: List<Business>,
        maxResults: Int = 20
    ): List<Business> {
        return businesses
            .filter { !it.hasWebsite }
            .take(maxResults)
    }

    /**
     * Calculates relevance score based on multiple factors.
     */
    fun calculateRelevanceScore(business: Business): Double {
        var score = 0.0

        // Rating score (0-5)
        score += (business.rating ?: 0.0) / 5.0 * 40.0

        // Review count score (normalized)
        val reviewScore = minOf(business.reviewCount / 100.0, 1.0)
        score += reviewScore * 30.0

        // Weighted score (already calculated)
        score += business.weightedScore * 20.0

        // Social presence score
        val socialHandles = listOfNotNull(
            business.instagram,
            business.facebook,
            business.tiktok,
            business.whatsapp
        ).size
        score += socialHandles * 5.0

        return score
    }
}
