package com.elsewhere.eyris.domain.usecase

import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactedBusinessesUseCase @Inject constructor(
    private val repository: BusinessRepository
) {
    operator fun invoke(status: ContactStatus? = null): Flow<List<Business>> {
        return if (status != null) {
            repository.getContactedBusinessesByStatus(status)
        } else {
            repository.getContactedBusinesses()
        }
    }
}
