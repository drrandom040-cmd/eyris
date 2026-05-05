package com.elsewhere.eyris.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsewhere.eyris.data.remote.firebase.FirebaseAuthManager
import com.elsewhere.eyris.data.remote.firebase.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthManager: FirebaseAuthManager,
    private val syncManager: SyncManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Checking)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (firebaseAuthManager.isUserSignedIn()) {
            val userId = firebaseAuthManager.getCurrentUserId()
            if (userId != null) {
                _authState.value = AuthState.Authenticated(userId)
                syncManager.scheduleSyncWork(userId)
                Timber.d("User already authenticated: $userId")
            }
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = firebaseAuthManager.signInWithGoogle(idToken)
                result.onSuccess { userId ->
                    _authState.value = AuthState.Authenticated(userId)
                    syncManager.scheduleSyncWork(userId)
                    Timber.d("Google sign-in successful: $userId")
                }.onFailure { error ->
                    _errorMessage.value = error.message
                    Timber.e(error, "Google sign-in failed")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Timber.e(e, "Exception during Google sign-in")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signInAnonymously() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = firebaseAuthManager.signInAnonymously()
                result.onSuccess { userId ->
                    _authState.value = AuthState.Authenticated(userId)
                    syncManager.scheduleSyncWork(userId)
                    Timber.d("Anonymous sign-in successful: $userId")
                }.onFailure { error ->
                    _errorMessage.value = error.message
                    Timber.e(error, "Anonymous sign-in failed")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Timber.e(e, "Exception during anonymous sign-in")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = firebaseAuthManager.signOut()
                result.onSuccess {
                    _authState.value = AuthState.Unauthenticated
                    syncManager.cancelSyncWork()
                    Timber.d("Sign out successful")
                }.onFailure { error ->
                    _errorMessage.value = error.message
                    Timber.e(error, "Sign out failed")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Timber.e(e, "Exception during sign out")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

sealed class AuthState {
    object Checking : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(val userId: String) : AuthState()
}
