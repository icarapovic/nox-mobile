package dev.nox.news.ui

import java.time.Duration

fun Duration.formatAge(): String =
    if (toHours() < 1)
        "${toMinutes().coerceAtLeast(0)}m"
    else
        "${toHours()}h"
