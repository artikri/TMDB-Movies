package com.arti.tmdbmovies.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arti.tmdbmovies.ui.details.MovieDetailsActivity
import com.arti.tmdbmovies.ui.theme.TMDBMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksActivity : ComponentActivity() {
    private val viewModel: BookmarksViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsState()
                    BookmarksScreen(
                        uiState = uiState,
                        onMovieClick = { movieId ->
                            navigateToMovieDetails(movieId)
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
}
