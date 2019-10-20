package com.tigerspike.flickrfeed.domain.service

import com.tigerspike.flickrfeed.data.api.internal.FlickrApi
import com.tigerspike.flickrfeed.domain.model.ImagesResponse
import com.tigerspike.flickrfeed.domain.translator.ImagesResponseTranslator
import io.reactivex.Single
import javax.inject.Inject

class FlickrService @Inject constructor(
    private val flickrApi: FlickrApi,
    private val imagesResponseTranslator: ImagesResponseTranslator
) {

    fun getImages(
        format: String = "json",
        nojsoncallback: Int = 1
    ): Single<ImagesResponse> {
        return flickrApi
            .getImages(format, nojsoncallback)
            .map {
                imagesResponseTranslator.translateToDomain(it)
            }
    }

}