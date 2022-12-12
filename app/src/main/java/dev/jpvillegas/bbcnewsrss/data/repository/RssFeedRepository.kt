package dev.jpvillegas.bbcnewsrss.data.repository

import com.prof.rssparser.Parser
import dev.jpvillegas.bbcnewsrss.data.db.FeedDao
import dev.jpvillegas.bbcnewsrss.data.mappers.toFeedEntity
import dev.jpvillegas.bbcnewsrss.data.mappers.toFeed
import dev.jpvillegas.bbcnewsrss.domain.model.Feed
import dev.jpvillegas.bbcnewsrss.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RssFeedRepository @Inject constructor(
    private val client: OkHttpClient,
    private val parser: Parser,
    private val feedDao: FeedDao,
) : FeedRepository {

    override suspend fun getRssFeeds(urlList: List<String>): List<Feed> {
        val feedEntities = urlList
            .mapNotNull { url ->
                // Future: Check built-in cache in parser -> parser.getChannel(feedUrl)
                fetchFeedUrl(url)?.let {
                    url to it
                }
            }.map { (url, feedStr) ->
                parser.parse(feedStr).toFeedEntity(url = url)
            }

        feedDao.insertOrUpdate(feedEntities)
        // in case some feed wasn't fetched, we get all from DB
        return feedDao.getAll().map { it.toFeed() }
    }

    private suspend fun fetchFeedUrl(feedUrl: String): String? = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url(feedUrl)
            .build()

        try {
            client.newCall(request)
                .execute()
                .body?.string()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getRssFeedById(id: Int): Feed? {
        return feedDao.getById(id)?.toFeed()
    }
}