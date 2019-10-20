package com.tigerspike.flickrfeed.app

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tigerspike.flickrfeed.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

open class FlickrApplication : DaggerApplication() {
    @Inject
    lateinit var dataBindingComponent: DataBindingComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

}