package dev.jpvillegas.bbcnewsrss.presentation.rss_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.bbcnewsrss.domain.repository.FetchState
import dev.jpvillegas.bbcnewsrss.domain.repository.RepositoryError
import dev.jpvillegas.bbcnewsrss.domain.use_case.GetRssFeedUseCase
import dev.jpvillegas.bbcnewsrss.presentation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RssFeedViewModel @Inject constructor(
    private val getRssFeedUseCase: GetRssFeedUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(RssFeedState())
    val state: State<RssFeedState> = _state

    init {
        savedStateHandle.get<String>(Screen.PARAM_FEED_ID)?.let {
            getFeedById(it.toInt())
        }
    }

    private fun getFeedById(id: Int) {
        getRssFeedUseCase
            .getFeedById(id)
            .flowOn(Dispatchers.IO)
            .onEach {
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
                            feed = it.data,
                            error = RepositoryError.None
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}