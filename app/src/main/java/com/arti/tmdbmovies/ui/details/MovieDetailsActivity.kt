package com.arti.tmdbmovies.ui.details

import android.content.Intent
import android.net.Uri
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
import com.arti.tmdbmovies.ui.theme.TMDBMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val movieId = getMovieIdFromIntent()
        viewModel.loadMovieDetails(movieId)
        
        setContent {
            TMDBMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsState()
                    MovieDetailsScreen(
                        uiState = uiState,
                        onBookmarkClick = { viewModel.toggleBookmark(movieId) },
                        onShareClick = { shareMovie(movieId) }
                    )
                }
            }
        }
    }

    private fun getMovieIdFromIntent(): Int {
        return when {
            intent.data != null -> {
                val uri: Uri = intent.data!!
                val path = uri.path
                path?.substringAfterLast("/")?.toIntOrNull() ?: -1
            }
            else -> intent.getIntExtra("MOVIE_ID", -1)
        }
    }

    private fun shareMovie(movieId: Int) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this movie! tmdbmovies://movie/$movieId")
        }
        startActivity(Intent.createChooser(shareIntent, "Share Movie"))
    }
}
