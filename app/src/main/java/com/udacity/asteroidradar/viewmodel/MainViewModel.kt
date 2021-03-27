package com.udacity.asteroidradar.viewmodel

import androidx.lifecycle.*
import androidx.work.*
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.repository.MainRepository
import com.udacity.asteroidradar.util.Constants.PAST
import com.udacity.asteroidradar.util.Constants.TODAY
import com.udacity.asteroidradar.util.Constants.TODAY_ONWARD
import com.udacity.asteroidradar.util.Constants.WORK_TAG
import com.udacity.asteroidradar.util.ResultData
import com.udacity.asteroidradar.worker.AsteroidWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataSource: MainRepository,
    private val workManager: WorkManager
) : ViewModel() {

    private var _loadingState = MutableLiveData<ResultData<*>>()
    val loadingState: LiveData<ResultData<*>> get() = _loadingState

    val workerState: LiveData<MutableList<WorkInfo>>
        get() = workManager.getWorkInfosByTagLiveData(WORK_TAG)

    private val listOfTodayOnwardsAsteroids: LiveData<List<AsteroidEntity?>> =
        dataSource.getAsteroidsTodayOnWardsFromLocal()

    private val listOfTodayAsteroids: LiveData<List<AsteroidEntity?>> =
        dataSource.getAsteroidsOfTodayFromLocal()

    private val listOfPastAsteroids = dataSource.getPastAsteroids()

    private var currentAsteroidList = TODAY_ONWARD

    val asteroidList = MediatorLiveData<List<AsteroidEntity?>>()

    val picOfDay = dataSource.getPictureFromLocal()

    init {

        asteroidList.addSource(listOfTodayOnwardsAsteroids) { listOfAsteroids ->
            if (currentAsteroidList == TODAY_ONWARD) {
                listOfAsteroids?.let { asteroidList.value = it }
            }
        }

        asteroidList.addSource(listOfTodayAsteroids) { listOfAsteroids ->
            if (currentAsteroidList == TODAY) {
                listOfAsteroids?.let { asteroidList.value = it }
            }
        }

        asteroidList.addSource(listOfPastAsteroids) { listOfAsteroids ->
            if (currentAsteroidList == PAST) {
                listOfAsteroids?.let { asteroidList.value = it }
            }
        }
    }

    fun sortAsteroidList(order: String) = when (order) {
        TODAY_ONWARD -> listOfTodayOnwardsAsteroids.value?.let { asteroidList.value = it }
        TODAY -> listOfTodayAsteroids.value?.let { asteroidList.value = it }
        PAST -> listOfPastAsteroids.value?.let { asteroidList.value = it }
        else -> {
        }
    }.also { currentAsteroidList = order }

    fun startWorker() {
        viewModelScope.launch {
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

            workManager
                .enqueueUniquePeriodicWork("work_name", ExistingPeriodicWorkPolicy.KEEP, request)
        }
    }

    fun startLoading() {
        _loadingState.value = ResultData.Loading
    }

    fun stopLoading() {
        _loadingState.value = ResultData.DoNothing
    }

}
