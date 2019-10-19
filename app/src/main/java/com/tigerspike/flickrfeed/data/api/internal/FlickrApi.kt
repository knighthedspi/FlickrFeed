package com.tigerspike.flickrfeed.data.api.internal

import com.tigerspike.flickrfeed.data.api.model.ImagesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("/services/feeds/photos_public.gne")
    fun getImages(
        @Query("format") format: String,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Single<ImagesResponse>
}