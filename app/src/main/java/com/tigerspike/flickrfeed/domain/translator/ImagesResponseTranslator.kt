package com.tigerspike.flickrfeed.domain.translator

import com.tigerspike.flickrfeed.domain.model.ImagesResponse
import com.tigerspike.flickrfeed.extension.toLocalDateTime
import javax.inject.Inject

typealias RawImagesResponse = com.tigerspike.flickrfeed.data.api.model.ImagesResponse

class ImagesResponseTranslator @Inject constructor(
    private val imageItemTranslator: ImageItemTranslator
) : TranslateToDomain<RawImagesResponse, ImagesResponse> {

    companion object {
        private const val MODIFIED_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    override fun translateToDomain(source: RawImagesResponse) = ImagesResponse(
        source.title,
        source.link,
        source.description,
        source.modified.toLocalDateTime(MODIFIED_FORMATTER),
        source.generator,
        source.items.map {
            imageItemTranslator.translateToDomain(it)
        }
    )
}