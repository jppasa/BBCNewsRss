package dev.jpvillegas.bbcnewsrss.domain.repository

import dev.jpvillegas.bbcnewsrss.domain.model.Feed
import java.io.IOException

interface FeedRepository {

    @Throws(IOException::class, Exception::class)
    suspend fun getRssFeeds(urlList: List<String>) : List<Feed>

    @Throws(IOException::class, Exception::class)
    suspend fun getRssFeedById(id: Int) : Feed?
}