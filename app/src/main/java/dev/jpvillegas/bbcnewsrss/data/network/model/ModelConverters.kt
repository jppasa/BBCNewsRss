package dev.jpvillegas.bbcnewsrss.data.network.model

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeedItem

fun Channel.toRssFeed(url: String): RssFeed {
    return RssFeed(
        title = title,
        description = description,
        link = link,
        thumbnailUrl = image?.url,
        url = url,
        items = articles.toRssFeedItems(),
    )
}

fun List<Article>.toRssFeedItems(): List<RssFeedItem> {
    return map { it.toRssFeedItem() }
}

fun Article.toRssFeedItem(): RssFeedItem {
    return RssFeedItem(
        title = title,
        description = description,
        link = link,
        guid = guid,
        publicationDate = pubDate,
    )
}
