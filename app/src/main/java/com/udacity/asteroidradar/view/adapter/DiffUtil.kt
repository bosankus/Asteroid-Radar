package com.udacity.asteroidradar.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.data.room.AsteroidEntity

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/
class DiffUtil : DiffUtil.ItemCallback<AsteroidEntity>() {
    override fun areItemsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}