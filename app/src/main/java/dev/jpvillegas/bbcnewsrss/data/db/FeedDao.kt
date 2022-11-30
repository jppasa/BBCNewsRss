package dev.jpvillegas.bbcnewsrss.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feed: FeedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(feeds: List<FeedEntity>)

    @Update
    fun update(feed: FeedEntity) : Int

    @Query("SELECT * FROM FeedEntity WHERE id = :feedId LIMIT 1")
    fun getById(feedId: Int) : FeedEntity?

    @Query("SELECT * FROM FeedEntity WHERE url = :url LIMIT 1")
    fun getByUrl(url: String) : FeedEntity?

    @Query("SELECT * FROM FeedEntity")
    fun getAll(): List<FeedEntity>

    @Transaction
    fun insertOrUpdate(feeds: List<FeedEntity>) {
        feeds.forEach { feed ->
            val storedFeed = getByUrl(feed.url)

            if (storedFeed != null) {
                update(feed.copy(id = storedFeed.id))
            } else {
                insert(feed)
            }
        }
    }
}