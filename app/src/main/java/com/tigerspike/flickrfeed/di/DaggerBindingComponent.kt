package com.tigerspike.flickrfeed.di

import androidx.databinding.DataBindingComponent
import com.tigerspike.flickrfeed.ui.adapter.CustomBindingAdapter
import javax.inject.Inject

/**
 *  use CustomBindingAdapter for data binding
 */
class DaggerBindingComponent @Inject constructor(
    private val adapter: CustomBindingAdapter
) : DataBindingComponent {
    override fun getCustomBindingAdapter(): CustomBindingAdapter = adapter
}