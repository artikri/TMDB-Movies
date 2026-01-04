package com.arti.tmdbmovies.domain.usecase

import com.arti.tmdbmovies.domain.model.MovieDetails
import com.arti.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails = repository.getMovieDetails(movieId)
}

