package com.tigerspike.flickrfeed.di

import android.app.Application
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingComponent
import androidx.preference.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tigerspike.flickrfeed.app.FlickrApplication
import com.tigerspike.flickrfeed.data.api.internal.FlickrApi
import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * App Module
 *
 * Support @Singleton
 * https://medium.com/square-corner-blog/keeping-the-daggers-sharp-%EF%B8%8F-230b3191c3f
 **/
@Module
object AppModule {

    @VisibleForTesting
    var API_BASE_URL = "https://www.flickr.com"

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
    fun retrofit(moshi: Moshi, callFactory: Call.Factory): Retrofit {
        return Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .callFactory(callFactory)
            .build()
    }

    @JvmStatic @Provides
    fun flickrApi(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
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
