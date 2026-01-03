package com.arti.tmdbmovies.data.remote.api

import com.arti.tmdbmovies.data.remote.model.TMDBMovieDetailsModel
import com.arti.tmdbmovies.data.remote.model.TMDBMoviesResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): TMDBMoviesResponseModel

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1
    ): TMDBMoviesResponseModel

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): TMDBMoviesResponseModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): TMDBMovieDetailsModel
}

