package com.tigerspike.flickrfeed.domain.model

import org.threeten.bp.LocalDateTime

data class ImagesResponse(
    val title: String,
    val link: String,
    val description: String,
    val modified: LocalDateTime,
    val generator: String,
    val items: List<ImageItem>
)