package dev.jpvillegas.bbcnewsrss.data.mappers

import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItem
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeedItem

fun FeedEntity.toRssFeed(): RssFeed {
    return RssFeed(
        id = id,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = thumbnailUrl,
        url = url,
        items = items.map { it.toRssFeedItem() },
    )
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