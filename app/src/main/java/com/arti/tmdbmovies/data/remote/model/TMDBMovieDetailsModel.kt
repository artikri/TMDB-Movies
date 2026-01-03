package com.arti.tmdbmovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class TMDBMovieDetailsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("genres")
    val genres: List<TMDBGenreModel>,
    @SerializedName("production_companies")
    val productionCompanies: List<TMDBProductionCompanyModel>?
)

data class TMDBGenreModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)

data class TMDBProductionCompanyModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String?
)

