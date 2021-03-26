package com.udacity.asteroidradar.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**Created by
Author: Ankush Bose
Date: 25,March,2021
 **/

@Entity(tableName = "picture_table")
data class PicOfDayEntity(

    @ColumnInfo(name = "media_type")
    var mediaType: String,

    @ColumnInfo(name = "image_title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var url: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}