package com.udacity.asteroidradar

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.udacity.asteroidradar.util.Constants.WORK_TAG
import com.udacity.asteroidradar.worker.AsteroidWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/

@HiltAndroidApp
class AsteroidRadarApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        applicationScope.launch {
            val workConstraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<AsteroidWorker>(24, TimeUnit.HOURS)
                .setConstraints(workConstraints)
                .addTag(WORK_TAG)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                ).build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork("work_name", ExistingPeriodicWorkPolicy.KEEP, request)
        }
    }
}