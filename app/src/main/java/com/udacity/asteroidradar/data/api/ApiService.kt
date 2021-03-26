package com.udacity.asteroidradar.data.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.util.today
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/
interface ApiService {

    @GET("/planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") key: String = BuildConfig.ApiKey
    ): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") startDate: String = today,
        @Query("api_key") apiKey: String = BuildConfig.ApiKey,
    ): ResponseBody
}