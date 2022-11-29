package dev.jpvillegas.bbcnewsrss.data.repository

import android.util.Log
import com.prof.rssparser.Parser
import dev.jpvillegas.bbcnewsrss.common.Constants
import dev.jpvillegas.bbcnewsrss.data.network.model.toRssFeed
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
) : RssFeedRepository {

    override suspend fun getRssFeeds(): List<RssFeed> {
        return withContext(Dispatchers.IO) {
            Constants.rssFeedUrls.mapNotNull { feedUrl ->
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
                Log.d("FetchRssFeeds", "Parsing $url -> ${feedStr.take(10)}")
                parser.parse(feedStr).toRssFeed(url)
            }
        }
    }
}