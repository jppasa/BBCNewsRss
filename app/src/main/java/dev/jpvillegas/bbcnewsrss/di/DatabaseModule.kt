package dev.jpvillegas.bbcnewsrss.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jpvillegas.bbcnewsrss.data.db.AppDb
import dev.jpvillegas.bbcnewsrss.data.db.FeedDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideFeedDao(db: AppDb): FeedDao {
        return db.feedDao()
    }

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context) : AppDb {
        return Room.databaseBuilder(
            appContext,
            AppDb::class.java,
            AppDb.DATABASE_NAME
        ).build()
    }
}