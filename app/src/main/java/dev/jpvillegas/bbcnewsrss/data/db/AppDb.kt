package dev.jpvillegas.bbcnewsrss.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.jpvillegas.bbcnewsrss.data.db.AppDb.Companion.DB_VERSION
import dev.jpvillegas.bbcnewsrss.data.db.converters.FeedItemListConverter
import dev.jpvillegas.bbcnewsrss.data.db.model.FeedEntity

@Database(
    entities = [
        FeedEntity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(
    FeedItemListConverter::class,
)
abstract class AppDb : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    companion object {
        const val DATABASE_NAME = "rss_app_db"
        const val DB_VERSION = 1
    }
}