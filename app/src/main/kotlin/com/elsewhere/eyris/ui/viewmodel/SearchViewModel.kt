package com.elsewhere.eyris.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsewhere.eyris.domain.model.Business
import com.elsewhere.eyris.domain.model.SearchResult
import com.elsewhere.eyris.domain.usecase.SearchBusinessesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBusinessesUseCase: SearchBusinessesUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Business>>(emptyList())
    val searchResults: StateFlow<List<Business>> = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateLocation(location: String) {
        _location.value = location
    }

    fun updateCategory(category: String) {
        _category.value = category
    }

    fun search(latitude: Double, longitude: Double) {
        if (_searchQuery.value.isBlank() || _location.value.isBlank()) {
            _searchState.value = SearchState.Error("Please enter search query and location")
            return
        }

        viewModelScope.launch {
            _searchState.value = SearchState.Loading
            try {
                val result = searchBusinessesUseCase(
                    query = _searchQuery.value,
                    location = _location.value,
                    latitude = latitude,
                    longitude = longitude
                )

                result.onSuccess { searchResult ->
                    _searchResults.value = searchResult.businesses
                    _searchState.value = SearchState.Success(searchResult)
                    Timber.d("Search successful: ${searchResult.businesses.size} results")
                }.onFailure { error ->
                    _searchState.value = SearchState.Error(error.message ?: "Unknown error")
                    Timber.e(error, "Search failed")
                }
            } catch (e: Exception) {
                _searchState.value = SearchState.Error(e.message ?: "Unknown error")
                Timber.e(e, "Search exception")
            }
        }
    }

    fun clearResults() {
        _searchResults.value = emptyList()
        _searchState.value = SearchState.Idle
    }
}

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val result: SearchResult) : SearchState()
    data class Error(val message: String) : SearchState()
}
