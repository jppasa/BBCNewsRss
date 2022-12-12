package dev.jpvillegas.bbcnewsrss.data.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["url"], unique = true)])
data class FeedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url : String,
    val title: String?,
    val description: String?,
    val link: String?,
    val thumbnailUrl: String?,
    val items: List<FeedItemEntity> // could be stored as different entity
)