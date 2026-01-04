package com.arti.tmdbmovies.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arti.tmdbmovies.ui.bookmarks.BookmarksActivity
import com.arti.tmdbmovies.ui.details.MovieDetailsActivity
import com.arti.tmdbmovies.ui.search.SearchActivity
import com.arti.tmdbmovies.ui.theme.TMDBMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        viewModel = viewModel,
                        onMovieClick = { movieId ->
                            navigateToMovieDetails(movieId)
                        },
                        onSearchClick = {
                            navigateToSearch()
                        },
                        onBookmarksClick = {
                            navigateToBookmarks()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToMovieDetails(movieId: Int) {
        Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra("MOVIE_ID", movieId)
            startActivity(this)
        }
    }

    private fun navigateToSearch() {
        Intent(this, SearchActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun navigateToBookmarks() {
        Intent(this, BookmarksActivity::class.java).apply {
            startActivity(this)
        }
    }
}
