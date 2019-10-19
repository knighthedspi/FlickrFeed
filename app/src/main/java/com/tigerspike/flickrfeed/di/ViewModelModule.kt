package com.tigerspike.flickrfeed.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun daggerViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

}