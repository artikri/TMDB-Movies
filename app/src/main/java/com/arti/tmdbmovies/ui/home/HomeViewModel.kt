package com.arti.tmdbmovies.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.usecase.GetNowPlayingMoviesUseCase
import com.arti.tmdbmovies.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            combine(
                getTrendingMoviesUseCase(),
                getNowPlayingMoviesUseCase()
            ) { trending, nowPlaying ->
                HomeUiState(
                    trendingMovies = trending,
                    nowPlayingMovies = nowPlaying,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }
}

data class HomeUiState(
    val trendingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)
