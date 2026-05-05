package com.elsewhere.eyris.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class BusinessEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
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
    val source: String,
    val savedAt: Long,
    val searchQuery: String? = null,
    val synced: Boolean = false
)

@Entity(tableName = "contacted_businesses")
data class ContactedBusinessEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val businessId: String,
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
    val source: String,
    val status: String, // ANSWERED, ACCEPTED, REJECTED, GHOSTED
    val notes: String? = null,
    val socialHandleTapped: String? = null,
    val contactedAt: Long,
    val lastUpdatedAt: Long,
    val synced: Boolean = false
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val displayName: String,
    val email: String,
    val lastOnline: Long,
    val createdAt: Long
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val query: String,
    val location: String,
    val category: String,
    val resultCount: Int,
    val timestamp: Long
)
