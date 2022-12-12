package dev.jpvillegas.bbcnewsrss.data.repository

import com.prof.rssparser.*
import dev.jpvillegas.bbcnewsrss.data.db.FeedDao
import dev.jpvillegas.bbcnewsrss.data.mappers.toFeedEntity
import dev.jpvillegas.bbcnewsrss.data.mappers.toRssFeed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class RssFeedRepositoryTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var sourceUrls: List<String>
    private lateinit var parser: Parser
    private lateinit var feedDao: FeedDao

    private lateinit var rssFeedRepositoryImpl: RssFeedRepository

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        sourceUrls = listOf(mockServer.url("").toUrl().toString())

        parser = Parser.Builder().build()

        feedDao = mock(FeedDao::class.java)

        rssFeedRepositoryImpl = RssFeedRepository(
            client = OkHttpClient(),
            parser = parser,
            feedDao = feedDao
        )
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `Get Rss feeds with successful response`() = runTest {
        val baseUrl = mockServer.url("").toUrl().toString()
        val feedEntity = parser.parse(RssFeedRepositoryData.ResponseChannel).toFeedEntity(baseUrl)
        `when`(feedDao.insertOrUpdate(anyList())).then { }
        `when`(feedDao.getAll()).thenReturn(listOf(feedEntity))

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(RssFeedRepositoryData.ResponseChannel)
        )

        val result = rssFeedRepositoryImpl.getRssFeeds(sourceUrls)

        assertEquals(result.size, sourceUrls.size)
        verify(feedDao, times(1)).insertOrUpdate(anyList())
        verify(feedDao, times(1)).getAll()
    }

    @Test
    fun `Get Rss feeds with error response`() = runTest {
        val baseUrl = mockServer.url("").toUrl().toString()
        val feedEntity = parser.parse(RssFeedRepositoryData.ResponseChannel).toFeedEntity(baseUrl)
        `when`(feedDao.insertOrUpdate(anyList())).then { }
        `when`(feedDao.getAll()).thenReturn(listOf(feedEntity))

        mockServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("")
        )

        val result = rssFeedRepositoryImpl.getRssFeeds(sourceUrls)

        // db fetch still should return all data
        assertEquals(result.size, sourceUrls.size)
        verify(feedDao, times(1)).insertOrUpdate(anyList())
        verify(feedDao, times(1)).getAll()
    }

    @Test
    fun getRssFeedById() = runTest {
        val baseUrl = mockServer.url("").toUrl().toString()
        val feedEntity = parser.parse(RssFeedRepositoryData.ResponseChannel).toFeedEntity(baseUrl)
        `when`(feedDao.getById(anyInt())).thenReturn(feedEntity)

        val result = rssFeedRepositoryImpl.getRssFeedById(0)

        assertNotNull(result)
        assertEquals(result, feedEntity.toRssFeed())
        verify(feedDao, times(1)).getById(anyInt())
    }
}