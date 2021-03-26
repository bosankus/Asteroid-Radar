package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.data.api.ApiService
import com.udacity.asteroidradar.data.room.AsteroidDao
import com.udacity.asteroidradar.data.room.PicOfDayDao
import com.udacity.asteroidradar.data.room.PicOfDayEntity
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.util.Constants.IMAGE_MEDIA
import com.udacity.asteroidradar.util.convertToAsteroidEntity
import com.udacity.asteroidradar.util.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val asteroidDao: AsteroidDao,
    private val picOfDayDao: PicOfDayDao
) {

    /**
     * Methods used to fetch asteroids from Room
     */
    fun getPictureFromLocal() = picOfDayDao.getPictureOfDayFromLocal()
    fun getAsteroidsTodayOnWardsFromLocal() = asteroidDao.getAsteroidsTodayOnWards()
    fun getAsteroidsOfTodayFromLocal() = asteroidDao.getAsteroidsOfToday()
    fun getPastAsteroids() = asteroidDao.getPastAsteroids()


    /**
     *  Methods used to fetch details from remote and save inside Room
     */
    suspend fun fetchAndSavePicture() {
        /**
         * Fetches the picture of the day data and checks if the media type is image.
         * If true, saves the image
         * */
        withContext(Dispatchers.IO) {
            val response = apiService.getPictureOfDay()
            if (response.mediaType == IMAGE_MEDIA) {
                val pictureEntity = PicOfDayEntity(response.mediaType, response.title, response.url)
                picOfDayDao.insertPicture(pictureEntity)
            }
        }
    }

    suspend fun fetchAndSaveAsteroids() {
        /**
         * Fetches the asteroid once a day and saves in Room
         */
        withContext(Dispatchers.IO) {
            val response: ResponseBody = apiService.getAsteroids()
            val asteroidList =
                parseAsteroidsJsonResult(JSONObject(response.string())) as List<Asteroid>
            asteroidList.convertToAsteroidEntity().forEach { asteroid ->
                asteroidDao.insertAsteroid(asteroid)
            }
        }
    }

}
