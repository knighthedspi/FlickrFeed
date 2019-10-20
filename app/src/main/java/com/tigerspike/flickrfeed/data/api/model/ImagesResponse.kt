package com.tigerspike.flickrfeed.data.api.model

data class ImagesResponse(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<ImageItem>
)