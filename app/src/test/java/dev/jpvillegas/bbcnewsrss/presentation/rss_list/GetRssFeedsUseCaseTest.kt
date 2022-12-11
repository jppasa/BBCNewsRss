package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import dev.jpvillegas.bbcnewsrss.domain.repository.FeedSourceRepository
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetRssFeedsUseCaseTest {

    private lateinit var getRssFeedsUseCase: GetRssFeedsUseCase
    private lateinit var repository: RssFeedRepository

    @Before
    fun setUp() {
        repository = mock(RssFeedRepository::class.java)

        val feedSourceRepository = object : FeedSourceRepository {
            override fun sourceUrls(): List<String> {
                return listOf("")
            }
        }

        getRssFeedsUseCase = GetRssFeedsUseCase(repository, feedSourceRepository)
    }

    @Test
    fun `Start Rss feeds fetch as loading state`() = runTest {
        val feedsFlow = getRssFeedsUseCase.getFeeds()
        val loadingState = feedsFlow.first()
        MatcherAssert.assertThat(loadingState, instanceOf(FetchState.Loading::class.java))
    }

    @Test
    fun `Get Rss feeds as successful`() = runTest {
        `when`(repository.getRssFeeds(anyList())).thenReturn(emptyList())
        val feedsFlow = getRssFeedsUseCase.getFeeds()
        val successState = feedsFlow.toList()[1]
        MatcherAssert.assertThat(successState, instanceOf(FetchState.Success::class.java))
    }

    @Test
    fun `Get Rss feeds with network error`() = runTest {
        `when`(repository.getRssFeeds(anyList())).thenThrow(IOException())
        val feedsFlow = getRssFeedsUseCase.getFeeds()
        val networkError = feedsFlow.toList()[1]
        MatcherAssert.assertThat(networkError, instanceOf(FetchState.Error::class.java))
        assertEquals(networkError.error, RepositoryError.Network)
    }

    @Test
    fun `Get Rss feeds with unknown error`() = runTest {
        `when`(repository.getRssFeeds(anyList())).thenThrow(Exception())
        val feedsFlow = getRssFeedsUseCase.getFeeds()
        val networkError = feedsFlow.toList()[1]
        MatcherAssert.assertThat(networkError, instanceOf(FetchState.Error::class.java))
        assertEquals(networkError.error, RepositoryError.Unknown)
    }
}