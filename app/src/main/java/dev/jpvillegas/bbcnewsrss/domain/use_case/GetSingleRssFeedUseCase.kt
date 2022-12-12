package dev.jpvillegas.bbcnewsrss.domain.use_case

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetSingleRssFeedUseCase @Inject constructor(
    private val repository: RssFeedRepository
) {
    fun getFeedById(id: Int): Flow<FetchState<RssFeed?>> {
        return flow {
            emit(FetchState.Loading())
            val feed = repository.getRssFeedById(id)
            emit(FetchState.Success(feed))
        }.catch { throwable ->
            if (throwable is IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } else {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }
    }
}