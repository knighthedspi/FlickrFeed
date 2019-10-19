package com.tigerspike.flickrfeed.domain.translator

import com.tigerspike.flickrfeed.domain.model.ImageMedia
import javax.inject.Inject

typealias RawImageMedia = com.tigerspike.flickrfeed.data.api.model.ImageMedia

class ImageMediaTranslator @Inject constructor() :
    TranslateToDomain<RawImageMedia, ImageMedia> {
    override fun translateToDomain(source: RawImageMedia) = ImageMedia(source.m)
}