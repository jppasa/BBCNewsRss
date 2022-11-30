package dev.jpvillegas.bbcnewsrss.data.repository

import com.prof.rssparser.Parser
import dev.jpvillegas.bbcnewsrss.common.Constants
import dev.jpvillegas.bbcnewsrss.data.db.FeedDao
import dev.jpvillegas.bbcnewsrss.data.toFeedEntity
import dev.jpvillegas.bbcnewsrss.data.toRssFeed
import dev.jpvillegas.bbcnewsrss.data.toRssFeedList
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RssFeedRepositoryImpl @Inject constructor(
    private val client: OkHttpClient,
    private val parser: Parser,
    private val feedDao: FeedDao,
) : RssFeedRepository {

    override suspend fun getRssFeeds(): List<RssFeed> {
        return withContext(Dispatchers.IO) {
            val feedEntities = Constants.rssFeedUrls.mapNotNull { feedUrl ->
                // Future: Check built-in cache in parser -> parser.getChannel(feedUrl)
                val request = Request.Builder()
                    .url(feedUrl)
                    .build()

                try {
                    client.newCall(request)
                        .execute()
                        .body?.string()
                } catch (e: Exception) {
                    null
                }?.let {
                    feedUrl to it
                }
            }.map { (url, feedStr) ->
                parser.parse(feedStr).toFeedEntity(url = url)
            }
            feedDao.insertOrUpdate(feedEntities)
            // in case some feed wasn't fetched, we get all from DB
            feedDao.getAll().toRssFeedList()
        }
    }

    override suspend fun getRssFeedById(id: Int): RssFeed? {
        return feedDao.getById(id)?.toRssFeed()
    }
}