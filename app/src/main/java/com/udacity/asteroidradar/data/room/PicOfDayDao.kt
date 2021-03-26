package com.udacity.asteroidradar.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**Created by
Author: Ankush Bose
Date: 25,March,2021
 **/

@Dao
interface PicOfDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(picture: PicOfDayEntity)

    @Query("SELECT * FROM picture_table")
    fun getPictureOfDayFromLocal(): LiveData<PicOfDayEntity?>
}