package com.tigerspike.flickrfeed.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.tigerspike.flickrfeed.di.GlideApp
import javax.inject.Inject

class CustomBindingAdapter @Inject constructor() {
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (url == null) {
            return
        }
        GlideApp.with(view)
            .load(url)
            .into(view)
    }
}