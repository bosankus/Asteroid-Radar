package com.udacity.asteroidradar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.databinding.LayoutAsteroidListItemBinding

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/
class AsteroidAdapter : ListAdapter<AsteroidEntity, AsteroidViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutAsteroidListItemBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroidItem = getItem(position)
        holder.bind(asteroidItem)
    }
}