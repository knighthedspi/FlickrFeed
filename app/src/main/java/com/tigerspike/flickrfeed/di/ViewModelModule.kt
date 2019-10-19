package com.tigerspike.flickrfeed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tigerspike.flickrfeed.ui.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun daggerViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    fun mainViewModel(model: MainViewModel): ViewModel

}