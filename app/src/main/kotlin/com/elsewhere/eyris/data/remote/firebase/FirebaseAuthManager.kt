package com.elsewhere.eyris.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signInWithGoogle(idToken: String): Result<String> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")
            Result.success(userId)
        } catch (e: Exception) {
            Timber.e(e, "Google sign-in failed")
            signInAnonymously()
        }
    }

    suspend fun signInAnonymously(): Result<String> {
        return try {
            val authResult = firebaseAuth.signInAnonymously().await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")
            Timber.i("Anonymous sign-in successful: $userId")
            Result.success(userId)
        } catch (e: Exception) {
            Timber.e(e, "Anonymous sign-in failed")
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun getCurrentUser() = firebaseAuth.currentUser

    fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Sign out failed")
            Result.failure(e)
        }
    }

    suspend fun deleteUser(): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.delete()?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Delete user failed")
            Result.failure(e)
        }
    }

    fun getAuthToken(): String? {
        return firebaseAuth.currentUser?.getIdToken(false)?.result?.token
    }
}
