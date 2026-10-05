package dev.nox.news.domain

class ContentNotAccessibleException(exception: Exception) : Exception(exception.message, exception)
