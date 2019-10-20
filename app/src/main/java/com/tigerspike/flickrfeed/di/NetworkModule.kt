package com.tigerspike.flickrfeed.di

import com.tigerspike.flickrfeed.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {
    private const val CONNECT_TIME_OUT = 30
    private const val READ_TIME_OUT = 30
    private const val WRITE_TIME_OUT = 30

    @JvmStatic
    @Provides
    fun callFactory(): Call.Factory {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

}