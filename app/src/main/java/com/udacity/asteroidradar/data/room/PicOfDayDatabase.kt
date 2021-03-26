package com.udacity.asteroidradar.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**Created by
Author: Ankush Bose
Date: 25,March,2021
 **/

@Database(
    entities = [PicOfDayEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PicOfDayDatabase: RoomDatabase() {

    abstract fun pictureDao(): PicOfDayDao
}