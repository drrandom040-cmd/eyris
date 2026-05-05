package com.elsewhere.eyris.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.ContactStatus
import com.elsewhere.eyris.domain.usecase.GetLeadsUseCase
import com.elsewhere.eyris.domain.usecase.GetContactedBusinessesUseCase
import com.elsewhere.eyris.domain.usecase.MoveToContactedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val getLeadsUseCase: GetLeadsUseCase,
    private val getContactedBusinessesUseCase: GetContactedBusinessesUseCase,
    private val moveToContactedUseCase: MoveToContactedUseCase
) : ViewModel() {

    private val _leads = MutableStateFlow<List<Business>>(emptyList())
    val leads: StateFlow<List<Business>> = _leads.asStateFlow()

    private val _contactedBusinesses = MutableStateFlow<List<Business>>(emptyList())
    val contactedBusinesses: StateFlow<List<Business>> = _contactedBusinesses.asStateFlow()

    private val _selectedTab = MutableStateFlow(0) // 0 = Leads, 1 = Contacted
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedStatus = MutableStateFlow<ContactStatus?>(null)
    val selectedStatus: StateFlow<ContactStatus?> = _selectedStatus.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadLeads()
        loadContactedBusinesses()
    }

    private fun loadLeads() {
        viewModelScope.launch {
            try {
                getLeadsUseCase().collect { businesses ->
                    _leads.value = businesses
                    Timber.d("Loaded ${businesses.size} leads")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error loading leads")
                _errorMessage.value = e.message
            }
        }
    }

    private fun loadContactedBusinesses() {
        viewModelScope.launch {
            try {
                getContactedBusinessesUseCase().collect { businesses ->
                    _contactedBusinesses.value = businesses
                    Timber.d("Loaded ${businesses.size} contacted businesses")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error loading contacted businesses")
                _errorMessage.value = e.message
            }
        }
    }

    fun selectTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun filterByStatus(status: ContactStatus?) {
        _selectedStatus.value = status
    }

    fun moveToContacted(
        business: Business,
        status: ContactStatus,
        notes: String? = null,
        socialHandle: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = moveToContactedUseCase(business, status, notes, socialHandle)
                result.onSuccess {
                    Timber.d("Business moved to contacted: ${business.name}")
                    loadLeads()
                    loadContactedBusinesses()
                }.onFailure { error ->
                    _errorMessage.value = error.message
                    Timber.e(error, "Error moving to contacted")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Timber.e(e, "Exception moving to contacted")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
