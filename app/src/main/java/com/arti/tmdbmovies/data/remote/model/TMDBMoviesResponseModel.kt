package com.arti.tmdbmovies.data.remote.model

import com.google.gson.annotations.SerializedName

data class TMDBMoviesResponseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TMDBMovieModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

