package com.udacity.asteroidradar.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**Created by
Author: Ankush Bose
Date: 22,March,2021
 **/


@Entity(tableName = "asteroids_table")
@Parcelize
data class AsteroidEntity(
    @ColumnInfo(name = "codename")
    var codename: String,

    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: String,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    var estimatedDiameter: Double,

    @ColumnInfo(name = "is_potentially_hazardous")
    var isPotentiallyHazardous: Boolean,

    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth")
    var distanceFromEarth: Double,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long
) : Parcelable