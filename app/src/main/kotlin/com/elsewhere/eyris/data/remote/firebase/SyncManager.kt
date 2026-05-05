package com.elsewhere.eyris.data.remote.firebase

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.elsewhere.eyris.workers.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val workManager = WorkManager.getInstance(context)

    fun scheduleSyncWork(userId: String) {
        try {
            val syncData = Data.Builder()
                .putString("userId", userId)
                .build()

            val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                15, TimeUnit.MINUTES  // Sync every 15 minutes
            )
                .setInputData(syncData)
                .setBackoffPolicy(
                    BackoffPolicy.EXPONENTIAL,
                    15, TimeUnit.MINUTES
                )
                .build()

            workManager.enqueueUniquePeriodicWork(
                SyncWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                syncWorkRequest
            )

            Timber.d("Sync work scheduled for user: $userId")
        } catch (e: Exception) {
            Timber.e(e, "Error scheduling sync work")
        }
    }

    fun cancelSyncWork() {
        try {
            workManager.cancelUniqueWork(SyncWorker.WORK_NAME)
            Timber.d("Sync work cancelled")
        } catch (e: Exception) {
            Timber.e(e, "Error cancelling sync work")
        }
    }

    fun observeSyncStatus() {
        workManager.getWorkInfosForUniqueWorkLiveData(SyncWorker.WORK_NAME)
            .observeForever { workInfoList ->
                workInfoList.forEach { workInfo ->
                    when {
                        workInfo.state.isFinished -> {
                            Timber.d("Sync work finished")
                        }
                        else -> {
                            Timber.d("Sync work running: ${workInfo.state}")
                        }
                    }
                }
            }
    }
}
