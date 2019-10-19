package com.tigerspike.flickrfeed.domain.translator

import com.tigerspike.flickrfeed.domain.model.ImageItem
import com.tigerspike.flickrfeed.extension.toLocalDateTime
import javax.inject.Inject

typealias RawImageItem = com.tigerspike.flickrfeed.data.api.model.ImageItem

class ImageItemTranslator @Inject constructor(
    private val imageMediaTranslator: ImageMediaTranslator
) : TranslateToDomain<RawImageItem, ImageItem> {

    companion object {
        private const val DATE_TAKEN_FORMATTER = "yyyy-MM-dd'T'HH:mm:ssVV"
        private const val PUBLISHED_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    override fun translateToDomain(source: RawImageItem) = ImageItem(
        source.title,
        source.link,
        imageMediaTranslator.translateToDomain(source.media),
        source.date_taken.toLocalDateTime(DATE_TAKEN_FORMATTER),
        source.description,
        source.published.toLocalDateTime(PUBLISHED_FORMATTER),
        source.author,
        source.author_id,
        source.tags
    )
}