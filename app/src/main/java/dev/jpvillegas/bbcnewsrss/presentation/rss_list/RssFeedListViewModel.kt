package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RssFeedListViewModel @Inject constructor(
    private val repository: RssFeedRepository
) : ViewModel() {

    private val _state = mutableStateOf(RssFeedListState())
    val state: State<RssFeedListState> = _state

    init {
        fetchRssFeeds() // TODO launch this from screen
    }

    fun fetchRssFeeds() {
        flow {
            try {
                emit(FetchState.Loading())
                val feeds = repository.getRssFeeds()
                emit(FetchState.Success(feeds))
            } catch (e: IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } catch (e: Exception) {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }.onEach {
            when (it) {
                is FetchState.Loading -> {
                    // TODO copy current state for loading and keeping list
                    _state.value = RssFeedListState(isLoading = true)
                }
                is FetchState.Error -> {
                    _state.value = RssFeedListState(error = it.error)
                }
                is FetchState.Success -> {
                    _state.value = RssFeedListState(
                        feeds = it.feeds ?: emptyList(),
                        error = RepositoryError.None
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}

sealed class FetchState(
    val feeds: List<RssFeed>? = null,
    val error: RepositoryError = RepositoryError.None
) {
    class Loading : FetchState()
    class Success(feeds: List<RssFeed>) : FetchState(feeds)
    class Error(error: RepositoryError) : FetchState(error = error)
}