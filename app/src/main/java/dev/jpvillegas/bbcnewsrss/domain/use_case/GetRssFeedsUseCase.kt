package dev.jpvillegas.bbcnewsrss.domain.use_case

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.SourceRepository
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetRssFeedsUseCase @Inject constructor(
    private val feedRepository: RssFeedRepository,
    private val sourceRepository: SourceRepository
) {
    fun getFeeds(): Flow<FetchState<List<RssFeed>>> {
        return flow {
            emit(FetchState.Loading())
            val urls = sourceRepository.sources()
            val feeds = feedRepository.getRssFeeds(urls)
            emit(FetchState.Success(feeds))
        }.catch { throwable ->
            if (throwable is IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } else {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }
    }
}