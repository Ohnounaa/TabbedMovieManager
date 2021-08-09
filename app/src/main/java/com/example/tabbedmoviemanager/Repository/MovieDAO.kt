package com.example.tabbedmoviemanager.Repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabbedmoviemanager.Models.Movie

@Dao
interface MovieDAO {

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun addMovie(movie: Movie)

@Query("SELECT * FROM movie_table")
fun fetchMovieData() : LiveData<List<Movie>>

@Delete
suspend fun removeMovie(movie: Movie)

}

