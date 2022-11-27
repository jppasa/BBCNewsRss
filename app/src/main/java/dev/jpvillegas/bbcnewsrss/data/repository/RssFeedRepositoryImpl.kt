package dev.jpvillegas.bbcnewsrss.data.repository

import com.prof.rssparser.Parser
import dev.jpvillegas.bbcnewsrss.common.Constants
import dev.jpvillegas.bbcnewsrss.data.network.model.toRssFeed
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RssFeedRepositoryImpl @Inject constructor(
    private val client: OkHttpClient,
    private val parser: Parser,
) : RssFeedRepository {

    override suspend fun getRssFeeds(): List<RssFeed> {
        return Constants.rssFeedUrls.mapNotNull { feedUrl ->
            // Future: Check built-in cache in parser -> parser.getChannel(feedUrl)

            val request = Request.Builder()
                .url(feedUrl)
                .build()

            val response = client
                .newCall(request)
                .execute()

            val body = response.body?.string()

            if (body == null) null
            else feedUrl to body
        }.map { (url, feedStr) ->
            parser.parse(feedStr).toRssFeed(url)
        }
    }
}