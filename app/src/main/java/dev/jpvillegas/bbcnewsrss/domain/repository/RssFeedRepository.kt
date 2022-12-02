package dev.jpvillegas.bbcnewsrss.domain.repository

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import java.io.IOException

interface RssFeedRepository {

    @Throws(IOException::class, Exception::class)
    suspend fun getRssFeeds() : List<RssFeed>

    suspend fun getRssFeedById(id: Int) : RssFeed?
}