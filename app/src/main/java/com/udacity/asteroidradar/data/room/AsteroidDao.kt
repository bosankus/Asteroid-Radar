package com.udacity.asteroidradar.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.util.today

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: AsteroidEntity)

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date >= :date ORDER by close_approach_date ASC")
    fun getAsteroidsTodayOnWards(date: String = today): LiveData<List<AsteroidEntity?>>

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date < :date ORDER by close_approach_date DESC")
    fun getPastAsteroids(date: String = today): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids_table WHERE close_approach_date = :date")
    fun getAsteroidsOfToday(date: String = today): LiveData<List<AsteroidEntity?>>
}