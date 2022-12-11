package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import dev.jpvillegas.bbcnewsrss.domain.use_case.GetSingleRssFeedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetSingleRssFeedUseCaseTest {

    private lateinit var repository: RssFeedRepository
    private lateinit var getSingleRssFeedUseCase: GetSingleRssFeedUseCase

    @Before
    fun setUp() {
        repository = Mockito.mock(RssFeedRepository::class.java)
        getSingleRssFeedUseCase = GetSingleRssFeedUseCase(repository)
    }

    @Test
    fun `Start Rss feeds fetch as loading state`() = runTest {
        val feedsFlow = getSingleRssFeedUseCase.getFeedById(anyInt())
        val loadingState = feedsFlow.first()
        MatcherAssert.assertThat(
            loadingState,
            IsInstanceOf.instanceOf(FetchState.Loading::class.java)
        )
    }

    @Test
    fun `Get Rss feeds as successful`() = runTest {
        Mockito.`when`(repository.getRssFeeds(anyList())).thenReturn(emptyList())
        val feedsFlow = getSingleRssFeedUseCase.getFeedById(anyInt())
        val successState = feedsFlow.toList()[1]
        MatcherAssert.assertThat(
            successState,
            IsInstanceOf.instanceOf(FetchState.Success::class.java)
        )
    }

    @Test
    fun `Get Rss feeds with network error`() = runTest {
        Mockito.`when`(repository.getRssFeedById(anyInt())).thenThrow(IOException())
        val feedsFlow = getSingleRssFeedUseCase.getFeedById(0)
        val networkError = feedsFlow.toList()[1]
        MatcherAssert.assertThat(
            networkError,
            IsInstanceOf.instanceOf(FetchState.Error::class.java)
        )
        Assert.assertEquals(networkError.error, RepositoryError.Network)
    }

    @Test
    fun `Get Rss feeds with unknown error`() = runTest {
        Mockito.`when`(repository.getRssFeedById(anyInt())).thenThrow(Exception())
        val feedsFlow = getSingleRssFeedUseCase.getFeedById(0)
        val networkError = feedsFlow.toList()[1]
        MatcherAssert.assertThat(
            networkError,
            IsInstanceOf.instanceOf(FetchState.Error::class.java)
        )
        Assert.assertEquals(networkError.error, RepositoryError.Unknown)
    }
}