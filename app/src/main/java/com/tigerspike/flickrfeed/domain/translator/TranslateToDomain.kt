package com.tigerspike.flickrfeed.domain.translator

interface TranslateToDomain<F, T> {
    fun translateToDomain(source: F): T
}