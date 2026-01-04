package com.arti.tmdbmovies.data.mapper

import com.arti.tmdbmovies.data.local.database.entity.BookmarkEntity
import com.arti.tmdbmovies.data.local.database.entity.MovieEntity
import com.arti.tmdbmovies.data.remote.model.TMDBMovieDetailsModel
import com.arti.tmdbmovies.data.remote.model.TMDBMovieModel
import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.model.MovieDetails

fun TMDBMovieModel.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity
    )
}

fun TMDBMovieDetailsModel.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        runtime = runtime,
        genres = genres.map { it.name },
        productionCompanies = productionCompanies?.map { it.name } ?: emptyList()
    )
}

fun TMDBMovieDetailsModel.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity
    )
}

fun Movie.toEntity(category: String): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        category = category
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity
    )
}

fun Int.toBookmarkEntity(): BookmarkEntity {
    return BookmarkEntity(movieId = this)
}
