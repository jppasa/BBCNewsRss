package dev.jpvillegas.bbcnewsrss.domain.model

data class FeedItem(
    val title: String?,
    val description: String?,
    val link: String?,
    val guid: String?,
    val publicationDate: String?,
)
