package com.udacity.asteroidradar.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.room.AsteroidEntity
import com.udacity.asteroidradar.view.adapter.AsteroidAdapter

@BindingAdapter("isLoading")
fun ProgressBar.bindLoadingState(responseState: ResultData<*>?) {
    this.visibility = when (responseState) {
        is ResultData.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("android:contentDescription")
fun ImageView.bindContentDescription(isHazardous: Boolean) {
    this.contentDescription =
        if (isHazardous) resources.getString(R.string.hazardous_text)
        else resources.getString(R.string.not_hazardous_text)
}

@BindingAdapter("pictureOfDay")
fun ImageView.bindPictureOfDay(url: String?) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.placeholder_picture_of_day)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(list: MediatorLiveData<List<AsteroidEntity?>>) {
    list.value.let {
        val asteroidAdapter = AsteroidAdapter()
        this.adapter = asteroidAdapter
        asteroidAdapter.submitList(it)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
