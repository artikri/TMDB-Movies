package com.arti.tmdbmovies.domain.usecase

import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String): List<Movie> = repository.searchMovies(query)
}

