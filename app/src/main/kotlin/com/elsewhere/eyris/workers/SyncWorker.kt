package com.elsewhere.eyris.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.elsewhere.eyris.data.local.room.dao.BusinessDao
import com.elsewhere.eyris.data.remote.firebase.FirestoreManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val businessDao: BusinessDao,
    private val firestoreManager: FirestoreManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Timber.d("Starting sync worker")

            val userId = inputData.getString("userId") ?: run {
                Timber.w("No userId provided to SyncWorker")
                return Result.retry()
            }

            // Sync unsynced businesses (leads)
            val unsyncedLeads = businessDao.getUnsyncedBusinesses()
            if (unsyncedLeads.isNotEmpty()) {
                Timber.d("Syncing ${unsyncedLeads.size} unsynced leads")
                val result = firestoreManager.saveLeads(userId, unsyncedLeads)
                if (result.isSuccess) {
                    unsyncedLeads.forEach { lead ->
                        businessDao.markBusinessSynced(lead.id)
                    }
                    Timber.d("Successfully synced leads")
                } else {
                    Timber.e("Failed to sync leads")
                    return Result.retry()
                }
            }

            // Sync unsynced contacted businesses
            val unsyncedContacted = businessDao.getUnsyncedContactedBusinesses()
            if (unsyncedContacted.isNotEmpty()) {
                Timber.d("Syncing ${unsyncedContacted.size} unsynced contacted businesses")
                val result = firestoreManager.saveContactedBusinesses(userId, unsyncedContacted)
                if (result.isSuccess) {
                    unsyncedContacted.forEach { contacted ->
                        businessDao.markContactedBusinessSynced(contacted.id)
                    }
                    Timber.d("Successfully synced contacted businesses")
                } else {
                    Timber.e("Failed to sync contacted businesses")
                    return Result.retry()
                }
            }

            Timber.d("Sync worker completed successfully")
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Sync worker failed with exception")
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "eyris_sync_work"
    }
}
