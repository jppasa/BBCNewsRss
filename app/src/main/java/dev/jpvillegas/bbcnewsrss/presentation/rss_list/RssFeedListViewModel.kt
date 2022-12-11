package dev.jpvillegas.bbcnewsrss.presentation.rss_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.use_case.GetRssFeedsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RssFeedListViewModel @Inject constructor(
    private val getRssFeedsUseCase: GetRssFeedsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RssFeedListState())
    val state: State<RssFeedListState> = _state

    init {
        fetchRssFeeds() // TODO launch this from screen
    }

    private fun fetchRssFeeds() {
        getRssFeedsUseCase
            .getFeeds()
            .flowOn(Dispatchers.IO)
            .onEach {
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
                            feeds = it.data ?: emptyList(),
                            error = RepositoryError.None
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}
