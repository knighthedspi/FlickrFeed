package com.tigerspike.flickrfeed.domain.model

import org.threeten.bp.LocalDateTime

data class ImageItem(
    val title: String,
    val link: String,
    val media: ImageMedia,
    val date_taken: LocalDateTime,
    val description: String,
    val published: LocalDateTime,
    val author: String,
    val author_id: String,
    val tags: String
)