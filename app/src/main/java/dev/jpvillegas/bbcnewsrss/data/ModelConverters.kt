package dev.jpvillegas.bbcnewsrss.data

import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItem
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeedItem


//fun Channel.toRssFeed(url: String): RssFeed {
//    return RssFeed(
//        title = title,
//        description = description,
//        link = link,
//        thumbnailUrl = image?.url,
//        url = url,
//        items = articles.toRssFeedItems(),
//    )
//}
//
//fun List<Article>.toRssFeedItems(): List<RssFeedItem> {
//    return map { it.toRssFeedItem() }
//}
//
//fun Article.toRssFeedItem(): RssFeedItem {
//    return RssFeedItem(
//        title = title,
//        description = description,
//        link = link,
//        guid = guid,
//        publicationDate = pubDate,
//    )
//}

fun Channel.toFeedEntity(url: String): FeedEntity {
    return FeedEntity(
        id = 0,
        url = url,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = image?.url,
        items = articles.toFeedItems(),
    )
}

fun List<Article>.toFeedItems(): List<FeedItem> {
    return map { it.toFeedItem() }
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

fun List<FeedEntity>.toRssFeedList(): List<RssFeed> {
    return map { it.toRssFeed() }
}

fun FeedEntity.toRssFeed(): RssFeed {
    return RssFeed(
        id = id,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = thumbnailUrl,
        url = url,
        items = items.toRssFeedItemList(),
    )
}

fun List<FeedItem>.toRssFeedItemList(): List<RssFeedItem> {
    return map { it.toRssFeedItem() }
}

fun FeedItem.toRssFeedItem(): RssFeedItem {
    return RssFeedItem(
        title = title,
        description = description,
        link = link,
        guid = guid,
        publicationDate = publicationDate,
    )
}