package com.arti.tmdbmovies.di

import android.content.Context
import androidx.room.Room
import com.arti.tmdbmovies.data.local.database.MovieDatabase
import com.arti.tmdbmovies.data.local.database.dao.BookmarkDao
import com.arti.tmdbmovies.data.local.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun provideBookmarkDao(database: MovieDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}

