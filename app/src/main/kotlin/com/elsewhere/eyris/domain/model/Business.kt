package com.elsewhere.eyris.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Business(
    val id: String,
    val name: String,
    val category: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String? = null,
    val email: String? = null,
    val website: String? = null,
    val hasWebsite: Boolean = false,
    val rating: Double? = null,
    val reviewCount: Int = 0,
    val weightedScore: Double = 0.0,
    val openingHours: String? = null,
    val coverImageUrl: String? = null,
    val instagram: String? = null,
    val facebook: String? = null,
    val tiktok: String? = null,
    val whatsapp: String? = null,
    val source: String, // "google_maps", "foursquare", "osm"
    val savedAt: Long = System.currentTimeMillis(),
    val searchQuery: String? = null,
    val synced: Boolean = false
)

@Serializable
data class ContactedBusiness(
    val id: String,
    val business: Business,
    val status: ContactStatus,
    val notes: String? = null,
    val socialHandleTapped: String? = null,
    val contactedAt: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)

enum class ContactStatus {
    ANSWERED, ACCEPTED, REJECTED, GHOSTED
}

data class SearchResult(
    val businesses: List<Business>,
    val totalCount: Int,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)
