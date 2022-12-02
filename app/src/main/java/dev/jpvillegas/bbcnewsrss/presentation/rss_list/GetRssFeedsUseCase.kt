package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetRssFeedsUseCase @Inject constructor(
    private val repository: RssFeedRepository
) {
    fun getFeeds(): Flow<FetchState<List<RssFeed>>> {
        return flow {
            emit(FetchState.Loading())

            val repoError = try {
                val feeds = repository.getRssFeeds()
                emit(FetchState.Success(feeds))
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