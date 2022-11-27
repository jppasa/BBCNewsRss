package dev.jpvillegas.bbcnewsrss.di

import com.prof.rssparser.Parser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jpvillegas.bbcnewsrss.data.repository.RssFeedRepositoryImpl
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun providesRssParser(): Parser {
        return Parser.Builder().build()
    }

    @Provides
    @Singleton
    fun providesRssFeedRepository(
        okHttpClient: OkHttpClient,
        rssParser: Parser
    ): RssFeedRepository {
        return RssFeedRepositoryImpl(
            client = okHttpClient,
            parser = rssParser
        )
    }
}