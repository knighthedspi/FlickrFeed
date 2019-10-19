package com.tigerspike.flickrfeed.data.api.model

data class ImageItem(
    val title: String,
    val link: String,
    val media: ImageMedia,
    val date_taken: String,
    val description: String,
    val published: String,
    val author: String,
    val author_id: String,
    val tags: String
)