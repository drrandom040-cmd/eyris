package com.elsewhere.eyris.domain.usecase

import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeadsUseCase @Inject constructor(
    private val repository: BusinessRepository
) {
    operator fun invoke(): Flow<List<Business>> {
        return repository.getLeads()
    }
}
