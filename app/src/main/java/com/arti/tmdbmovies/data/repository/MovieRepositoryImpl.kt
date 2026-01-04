package com.arti.tmdbmovies.data.repository

import com.arti.tmdbmovies.data.local.database.dao.BookmarkDao
import com.arti.tmdbmovies.data.local.database.dao.MovieDao
import com.arti.tmdbmovies.data.mapper.toBookmarkEntity
import com.arti.tmdbmovies.data.mapper.toDomain
import com.arti.tmdbmovies.data.mapper.toEntity
import com.arti.tmdbmovies.data.mapper.toMovie
import com.arti.tmdbmovies.data.remote.api.TMDBService
import com.arti.tmdbmovies.domain.model.Movie
import com.arti.tmdbmovies.domain.model.MovieDetails
import com.arti.tmdbmovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieDao: MovieDao,
    private val bookmarkDao: BookmarkDao
) : MovieRepository {

    override fun getTrendingMovies(): Flow<List<Movie>> = flow {
        try {
            val cached = movieDao.getMoviesByCategory(CATEGORY_TRENDING).first().map { it.toDomain() }
            if (cached.isNotEmpty()) {
                emit(cached)
            }
        } catch (e: Exception) {
        }

        try {
            val response = tmdbService.getTrendingMovies()
            val movies = response.results.map { it.toDomain() }
            movieDao.deleteMoviesByCategory(CATEGORY_TRENDING)
            movieDao.insertMovies(movies.map { it.toEntity(CATEGORY_TRENDING) })
            emit(movies)
        } catch (e: Exception) {
            val cached = try {
                movieDao.getMoviesByCategory(CATEGORY_TRENDING).first().map { it.toDomain() }
            } catch (ex: Exception) {
                emptyList()
            }
            if (cached.isEmpty()) throw e
            emit(cached)
        }
    }

    override fun getNowPlayingMovies(): Flow<List<Movie>> = flow {
        try {
            val cached = movieDao.getMoviesByCategory(CATEGORY_NOW_PLAYING).first().map { it.toDomain() }
            if (cached.isNotEmpty()) {
                emit(cached)
            }
        } catch (e: Exception) {
        }

        try {
            val response = tmdbService.getNowPlayingMovies()
            val movies = response.results.map { it.toDomain() }
            movieDao.deleteMoviesByCategory(CATEGORY_NOW_PLAYING)
            movieDao.insertMovies(movies.map { it.toEntity(CATEGORY_NOW_PLAYING) })
            emit(movies)
        } catch (e: Exception) {
            val cached = try {
                movieDao.getMoviesByCategory(CATEGORY_NOW_PLAYING).first().map { it.toDomain() }
            } catch (ex: Exception) {
                emptyList()
            }
            if (cached.isEmpty()) throw e
            emit(cached)
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        try {
            val cached = movieDao.getMoviesByCategory(CATEGORY_SEARCH).first()
                .map { it.toDomain() }
                .filter { it.title.contains(query, ignoreCase = true) }
            if (cached.isNotEmpty()) {
                return cached
            }
        } catch (e: Exception) {
        }

        try {
            val response = tmdbService.searchMovies(query)
            val movies = response.results.map { it.toDomain() }
            movieDao.insertMovies(movies.map { it.toEntity(CATEGORY_SEARCH) })
            return movies
        } catch (e: Exception) {
            val cached = try {
                movieDao.getMoviesByCategory(CATEGORY_SEARCH).first()
                    .map { it.toDomain() }
                    .filter { it.title.contains(query, ignoreCase = true) }
            } catch (ex: Exception) {
                emptyList()
            }
            if (cached.isEmpty()) throw e
            return cached
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val cachedMovie = movieDao.getMovieById(movieId)

        try {
            val detailsModel = tmdbService.getMovieDetails(movieId)
            val details = detailsModel.toDomain()
            val movie = detailsModel.toMovie()
            movieDao.insertMovies(listOf(movie.toEntity(CATEGORY_DETAILS)))
            return details
        } catch (e: Exception) {
            if (cachedMovie != null) {
                val movie = cachedMovie.toDomain()
                return MovieDetails(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    posterPath = movie.posterPath,
                    backdropPath = movie.backdropPath,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                    voteCount = movie.voteCount,
                    popularity = movie.popularity,
                    runtime = null,
                    genres = emptyList(),
                    productionCompanies = emptyList()
                )
            }
            throw e
        }
    }

    override suspend fun bookmarkMovie(movieId: Int) {
        var movie = movieDao.getMovieById(movieId)
        if (movie == null) {
            try {
                val details = tmdbService.getMovieDetails(movieId)
                movie = details.toMovie().toEntity(CATEGORY_BOOKMARKED)
                movieDao.insertMovies(listOf(movie))
            } catch (e: Exception) {
                throw e
            }
        } else {
            if (movie.category != CATEGORY_BOOKMARKED) {
                movieDao.insertMovies(listOf(movie.copy(category = CATEGORY_BOOKMARKED)))
            }
        }
        bookmarkDao.insertBookmark(movieId.toBookmarkEntity())
    }

    override suspend fun unbookmarkMovie(movieId: Int) {
        bookmarkDao.getBookmark(movieId)?.let {
            bookmarkDao.deleteBookmark(it)
        }
    }

    override suspend fun isBookmarked(movieId: Int): Boolean {
        return bookmarkDao.isBookmarked(movieId)
    }

    override fun getBookmarkedMovies(): Flow<List<Movie>> {
        return bookmarkDao.getAllBookmarks().map { bookmarks ->
            bookmarks.mapNotNull { bookmark ->
                movieDao.getMovieById(bookmark.movieId)?.toDomain()
            }
        }
    }

    companion object {
        private const val CATEGORY_TRENDING = "trending"
        private const val CATEGORY_NOW_PLAYING = "now_playing"
        private const val CATEGORY_SEARCH = "search"
        private const val CATEGORY_DETAILS = "details"
        private const val CATEGORY_BOOKMARKED = "bookmarked"
    }
}
