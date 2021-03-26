package com.udacity.asteroidradar.view.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.databinding.LayoutAsteroidListItemBinding
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.view.main.MainFragmentDirections

/**Created by
Author: Ankush Bose
Date: 23,March,2021
 **/
class AsteroidViewHolder(private val binding: LayoutAsteroidListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroidItem: AsteroidEntity) {
        binding.apply {
            asteroid = asteroidItem
            layoutAsteroidItemClContainer.setOnClickListener {
                val action = MainFragmentDirections.actionShowDetail(asteroidItem)
                it.findNavController().navigate(action)
            }
            executePendingBindings()
        }
    }
}