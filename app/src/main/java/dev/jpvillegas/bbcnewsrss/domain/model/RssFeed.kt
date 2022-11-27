package dev.jpvillegas.bbcnewsrss.domain.model

data class RssFeed(
    val title: String?,
    val description: String?,
    val link: String?,
    val thumbnailUrl: String?,
    val url: String,
    val items: List<RssFeedItem>
)