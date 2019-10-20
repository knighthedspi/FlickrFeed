package com.tigerspike.flickrfeed.domain.model

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
) {
    fun getPublishedText(): String = published.format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    )
}