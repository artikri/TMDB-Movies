package com.arti.tmdbmovies.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val runtime: Int?,
    val genres: List<String>,
    val productionCompanies: List<String>
)

