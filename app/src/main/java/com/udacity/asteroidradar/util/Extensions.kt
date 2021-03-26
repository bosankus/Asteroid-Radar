package com.udacity.asteroidradar.util

import android.annotation.SuppressLint
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.util.Constants.API_QUERY_DATE_FORMAT
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**Created by
Author: Ankush Bose
Date: 24,March,2021
 **/

@SuppressLint("SimpleDateFormat", "WeekBasedYear")
val today: String = SimpleDateFormat(API_QUERY_DATE_FORMAT).format(Date())

fun logMessage(message: String) = Timber.i(message)

fun List<Asteroid>.convertToAsteroidEntity(): List<AsteroidEntity> {
    val asteroidEntityList = ArrayList<AsteroidEntity>()
    asteroidEntityList.addAll(this.map { asteroidItem ->
        AsteroidEntity(
            asteroidItem.codename,
            asteroidItem.closeApproachDate,
            asteroidItem.absoluteMagnitude,
            asteroidItem.estimatedDiameter,
            asteroidItem.isPotentiallyHazardous,
            asteroidItem.relativeVelocity,
            asteroidItem.distanceFromEarth,
            asteroidItem.id
        )
    })
    return asteroidEntityList
}



