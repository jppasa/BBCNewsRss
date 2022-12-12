package dev.jpvillegas.bbcnewsrss.data.db.model

// Named 'entity' now for simplicity but it's not actually a db entity (no table)
class FeedItemEntity(
    val title: String?,
    val description: String?,
    val link: String?,
    val guid: String?,
    val publicationDate: String?,
)