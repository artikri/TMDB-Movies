package com.arti.tmdbmovies.domain.usecase

import com.arti.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

class UnbookmarkMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.unbookmarkMovie(movieId)
}

