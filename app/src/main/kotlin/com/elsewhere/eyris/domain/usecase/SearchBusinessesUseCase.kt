package com.elsewhere.eyris.domain.usecase

import com.elsewhere.eyris.domain.model.SearchResult
import com.elsewhere.eyris.domain.repository.BusinessRepository
import javax.inject.Inject

class SearchBusinessesUseCase @Inject constructor(
    private val repository: BusinessRepository
) {
    suspend operator fun invoke(
        query: String,
        location: String,
        latitude: Double,
        longitude: Double
    ): Result<SearchResult> {
        return repository.searchBusinesses(query, location, latitude, longitude)
    }
}
