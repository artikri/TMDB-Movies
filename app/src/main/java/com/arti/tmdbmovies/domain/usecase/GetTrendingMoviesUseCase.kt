package com.arti.tmdbmovies.domain.usecase

import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getTrendingMovies()
}

