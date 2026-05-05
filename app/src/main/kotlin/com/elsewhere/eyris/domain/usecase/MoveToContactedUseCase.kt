package com.elsewhere.eyris.domain.usecase

import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.domain.repository.BusinessRepository
import javax.inject.Inject

class MoveToContactedUseCase @Inject constructor(
    private val repository: BusinessRepository
) {
    suspend operator fun invoke(
        business: Business,
        status: ContactStatus,
        notes: String? = null,
        socialHandle: String? = null
    ): Result<Unit> {
        return repository.moveToContacted(business, status, notes, socialHandle)
    }
}
