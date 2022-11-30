package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.bbcnewsrss.domain.model.RssFeed
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.repository.RssFeedRepository
import dev.jpvillegas.bbcnewsrss.presentation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RssFeedViewModel @Inject constructor(
    private val repository: RssFeedRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(RssFeedState())
    val state: State<RssFeedState> = _state

    init {
        savedStateHandle.get<Int>(Screen.PARAM_FEED_ID)?.let {
            getFeedById(it)
        }
    }

    private fun getFeedById(id: Int) {
        flow {
            try {
                emit(FetchState.Loading())
                val feed = repository.getRssFeedById(id)
                emit(FetchState.Success(feed))
            } catch (e: IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } catch (e: Exception) {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }.flowOn(Dispatchers.IO).onEach {
            when (it) {
                is FetchState.Loading -> {
                    // TODO copy current state for loading and keeping list
                    _state.value = RssFeedState(isLoading = true)
                }
                is FetchState.Error -> {
                    _state.value = RssFeedState(error = it.error)
                }
                is FetchState.Success -> {
                    _state.value = RssFeedState(
                        feed = it.feed,
                        error = RepositoryError.None
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class FetchState(
        val feed: RssFeed? = null,
        val error: RepositoryError = RepositoryError.None
    ) {
        class Loading : FetchState()
        class Success(feed: RssFeed?) : FetchState(feed)
        class Error(error: RepositoryError) : FetchState(error = error)
    }
}