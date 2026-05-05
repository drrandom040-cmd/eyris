package com.elsewhere.eyris.domain.repository

import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface BusinessRepository {
    
    // Search operations
    suspend fun searchBusinesses(
        query: String,
        location: String,
        latitude: Double,
        longitude: Double
    ): Result<SearchResult>

    // Lead operations
    suspend fun saveLead(business: Business): Result<Unit>
    suspend fun saveLeads(businesses: List<Business>): Result<Unit>
    suspend fun deleteLead(businessId: String): Result<Unit>
    fun getLeads(): Flow<List<Business>>
    suspend fun getLeadById(id: String): Business?

    // Contacted business operations
    suspend fun moveToContacted(
        business: Business,
        status: ContactStatus,
        notes: String? = null,
        socialHandle: String? = null
    ): Result<Unit>

    suspend fun updateContactStatus(
        businessId: String,
        status: ContactStatus,
        notes: String? = null
    ): Result<Unit>

    suspend fun deleteContacted(businessId: String): Result<Unit>
    fun getContactedBusinesses(): Flow<List<Business>>
    fun getContactedBusinessesByStatus(status: ContactStatus): Flow<List<Business>>
    suspend fun getContactedBusinessById(id: String): Business?

    // Sync operations
    suspend fun syncWithFirebase(): Result<Unit>
    suspend fun saveSearchHistory(
        query: String,
        location: String,
        category: String,
        resultCount: Int
    ): Result<Unit>
}
