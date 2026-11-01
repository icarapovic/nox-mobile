package dev.nox.news.domain.models

enum class Source(
    val tabColor: Long,
    val title: String
) {
    HN_TOP(0xFFEA7E15, "Top"),
    HN_NEW(0xFFEA7E15, "New"),
    HN_BEST(0xFFEA7E15, "Best"),
    HN_ASK(0xFFEA7E15, "Ask"),
    HN_SHOW(0xFFEA7E15, "Show"),
    HN_JOBS(0xFFEA7E15, "Jobs")
}