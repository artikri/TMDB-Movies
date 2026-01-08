package com.arti.tmdbmovies.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arti.tmdbmovies.ui.components.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onBookmarksClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("TMDB Movies") },
                actions = {
                    IconButton(onClick = onBookmarksClick) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(onClick = onSearchClick)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    placeholder = { Text("Search movies...") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    enabled = false
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Trending Movies",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                
                if (uiState.trendingMovies.isEmpty() && !uiState.isLoading) {
                    Text(
                        text = "No trending movies available",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(uiState.trendingMovies) { movie ->
                            MovieCard(
                                title = movie.title,
                                posterPath = movie.posterPath,
                                voteAverage = movie.voteAverage,
                                onClick = { onMovieClick(movie.id) },
                                modifier = Modifier.padding(horizontal = 4.dp),
                                isInRow = true
                            )
                        }
                    }
                }
                
                Text(
                    text = "Now Playing",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                
                if (uiState.nowPlayingMovies.isEmpty() && !uiState.isLoading) {
                    Text(
                        text = "No movies currently playing",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(uiState.nowPlayingMovies) { movie ->
                            MovieCard(
                                title = movie.title,
                                posterPath = movie.posterPath,
                                voteAverage = movie.voteAverage,
                                onClick = { onMovieClick(movie.id) },
                                modifier = Modifier.padding(horizontal = 4.dp),
                                isInRow = true
                            )
                        }
                    }
                }
            }
        }
    }
}
