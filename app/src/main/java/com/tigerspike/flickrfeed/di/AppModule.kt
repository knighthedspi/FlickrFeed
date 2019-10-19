package com.tigerspike.flickrfeed.di

import android.app.Application
import android.content.SharedPreferences
import androidx.databinding.DataBindingComponent
import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tigerspike.flickrfeed.app.FlickrApplication
import dagger.Module
import dagger.Provides

/**
 * App Module
 *
 * Support @Singleton
 * https://medium.com/square-corner-blog/keeping-the-daggers-sharp-%EF%B8%8F-230b3191c3f
 **/
@Module
object AppModule {

    @JvmStatic @Provides
    fun application(application: FlickrApplication): Application {
        return application
    }

    @JvmStatic @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @JvmStatic @Provides
    fun sharedPreferences(app: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @JvmStatic @Provides
    fun bindingComponent(component: DaggerBindingComponent): DataBindingComponent {
        return component
    }

}
