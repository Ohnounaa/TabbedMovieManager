package com.example.tabbedmoviemanager.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.tabbedmoviemanager.Repository.MovieTypeConverters

@Entity(tableName="movie_table")
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    @TypeConverters(MovieTypeConverters::class)
    val genre_ids: List<Int>,
    @PrimaryKey val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)