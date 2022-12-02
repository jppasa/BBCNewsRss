package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetRssFeedUseCase @Inject constructor(
    private val repository: RssFeedRepository
) {
    fun getFeedById(id: Int): Flow<FetchState<RssFeed?>> {
        return flow {
            emit(FetchState.Loading())

            val repoError = try {
                val feed = repository.getRssFeedById(id)
                emit(FetchState.Success(feed))
                RepositoryError.None
            } catch (e: IOException) {
                RepositoryError.Network
            } catch (e: Exception) {
                RepositoryError.Unknown
            }

            if (repoError != RepositoryError.None) {
                emit(FetchState.Error(error = repoError))
            }
        }
    }
}