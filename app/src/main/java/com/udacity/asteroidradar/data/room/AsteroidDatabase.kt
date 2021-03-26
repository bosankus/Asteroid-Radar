package com.udacity.asteroidradar.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/

@Database(
    entities = [AsteroidEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao
}

