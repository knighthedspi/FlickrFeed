package com.tigerspike.flickrfeed.di

import com.tigerspike.flickrfeed.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {

    @ActivityScope @ContributesAndroidInjector
    fun mainActivity(): MainActivity

}