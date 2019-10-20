package com.tigerspike.flickrfeed.di

import com.tigerspike.flickrfeed.app.FlickrApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

// Application components
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AndroidInjector<FlickrApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<FlickrApplication>
}