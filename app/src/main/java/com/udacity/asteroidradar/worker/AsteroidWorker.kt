package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.MainRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/

@HiltWorker
class AsteroidWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: MainRepository
) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.fetchAndSaveAsteroids()
            repository.fetchAndSavePicture()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
