package com.arti.tmdbmovies.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arti.tmdbmovies.domain.model.MovieDetails
import com.arti.tmdbmovies.domain.usecase.BookmarkMovieUseCase
import com.arti.tmdbmovies.domain.usecase.GetMovieDetailsUseCase
import com.arti.tmdbmovies.domain.usecase.IsBookmarkedUseCase
import com.arti.tmdbmovies.domain.usecase.UnbookmarkMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
    private val unbookmarkMovieUseCase: UnbookmarkMovieUseCase,
    private val isBookmarkedUseCase: IsBookmarkedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val details = getMovieDetailsUseCase(movieId)
                val isBookmarked = isBookmarkedUseCase(movieId)
                _uiState.update {
                    it.copy(
                        movieDetails = details,
                        isBookmarked = isBookmarked,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun toggleBookmark(movieId: Int) {
        viewModelScope.launch {
            val currentState = _uiState.value.isBookmarked
            try {
                if (currentState) {
                    unbookmarkMovieUseCase(movieId)
                } else {
                    bookmarkMovieUseCase(movieId)
                }
                _uiState.update { it.copy(isBookmarked = !currentState) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}

data class MovieDetailsUiState(
    val movieDetails: MovieDetails? = null,
    val isBookmarked: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

