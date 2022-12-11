package dev.jpvillegas.bbcnewsrss.data.mappers

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItem

fun Channel.toFeedEntity(url: String): FeedEntity {
    return FeedEntity(
        id = 0,
        url = url,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = image?.url,
        items = articles.map { it.toFeedItem() },
    )
}

fun Article.toFeedItem(): FeedItem {
    return FeedItem(
        title = title,
        description = description,
        link = link,
        guid = guid,
        publicationDate = pubDate,
    )
}