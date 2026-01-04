package com.arti.tmdbmovies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arti.tmdbmovies.data.local.database.dao.BookmarkDao
import com.arti.tmdbmovies.data.local.database.dao.MovieDao
import com.arti.tmdbmovies.data.local.database.entity.BookmarkEntity
import com.arti.tmdbmovies.data.local.database.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun bookmarkDao(): BookmarkDao
}

