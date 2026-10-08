package dev.nox.news.data.remote.hn.uc

import dev.nox.news.data.local.hn.HackerNewsDao
import dev.nox.news.domain.usecase.CheckHackerNewsStoryCached

class CheckHackerNewsStoryCachedImpl(
    val dao: HackerNewsDao
) : CheckHackerNewsStoryCached {

    override suspend fun isCached(id: Long): Boolean {
        return dao.containsStory(id)
    }
}