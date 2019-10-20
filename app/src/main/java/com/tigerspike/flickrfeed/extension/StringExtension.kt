package com.tigerspike.flickrfeed.extension

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

fun String.toLocalDateTime(
    pattern: String
): LocalDateTime {
    return LocalDateTime.from(
        DateTimeFormatter
            .ofPattern(pattern)
            .parse(this)
    )
}