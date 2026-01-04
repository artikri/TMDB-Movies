package com.arti.tmdbmovies.domain.repository

import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(): Flow<List<Movie>>
    fun getNowPlayingMovies(): Flow<List<Movie>>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun bookmarkMovie(movieId: Int)
    suspend fun unbookmarkMovie(movieId: Int)
    suspend fun isBookmarked(movieId: Int): Boolean
    fun getBookmarkedMovies(): Flow<List<Movie>>
}

