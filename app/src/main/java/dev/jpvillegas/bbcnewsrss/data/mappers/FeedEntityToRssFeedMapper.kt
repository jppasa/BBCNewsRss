package dev.jpvillegas.bbcnewsrss.data.mappers

import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItem
import dev.jpvillegas.bbcnewsrss.domain.model.Feed
import dev.jpvillegas.bbcnewsrss.domain.model.FeedItem

fun FeedEntity.toRssFeed(): Feed {
    return Feed(
        id = id,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = thumbnailUrl,
        url = url,
        items = items.map { it.toRssFeedItem() },
    )
}

fun FeedItem.toRssFeedItem(): dev.jpvillegas.bbcnewsrss.domain.model.FeedItem {
    return dev.jpvillegas.bbcnewsrss.domain.model.FeedItem(
        title = title,
        description = description,
        link = link,
        guid = guid,
        publicationDate = publicationDate,
    )
}