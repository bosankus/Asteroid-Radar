package com.udacity.asteroidradar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.repository.MainRepository
import com.udacity.asteroidradar.util.Constants.PAST
import com.udacity.asteroidradar.util.Constants.TODAY
import com.udacity.asteroidradar.util.Constants.TODAY_ONWARD
import com.udacity.asteroidradar.util.Constants.WORK_TAG
import com.udacity.asteroidradar.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataSource: MainRepository,
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
        startLoading()

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


    private fun startLoading() {
        _loadingState.value = ResultData.Loading
    }

    fun stopLoading() {
        _loadingState.value = ResultData.DoNothing
    }

}
