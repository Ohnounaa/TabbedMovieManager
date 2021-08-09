package com.example.tabbedmoviemanager.Models


import androidx.room.Entity
import com.example.tabbedmoviemanager.Models.Movie
@Entity
data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)