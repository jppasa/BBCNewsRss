package dev.jpvillegas.bbcnewsrss.data.mappers

import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedItemEntity
import dev.jpvillegas.bbcnewsrss.domain.model.Feed
import dev.jpvillegas.bbcnewsrss.domain.model.FeedItem

fun FeedEntity.toFeed(): Feed {
    return Feed(
        id = id,
        title = title,
        description = description,
        link = link,
        thumbnailUrl = thumbnailUrl,
        url = url,
        items = items.map { it.toFeedItemEntity() },
    )
}

fun FeedItemEntity.toFeedItemEntity(): FeedItem {
    return FeedItem(
        title = title,
        description = description,
        link = link,
        guid = guid,
        publicationDate = publicationDate,
    )
}