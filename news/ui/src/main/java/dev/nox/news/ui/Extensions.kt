package dev.nox.news.ui

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Duration.formatAge(): String =
    if (toHours() < 1)
        "${toMinutes().coerceAtLeast(0)}m"
    else
        "${toHours()}h"

fun Long.formatTimestampBookmark(): String {
    return DateTimeFormatter.ofPattern("dd MMM yyyy · HH:mm")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))
}