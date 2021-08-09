package com.example.tabbedmoviemanager.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabbedmoviemanager.Models.Movie
import com.example.tabbedmoviemanager.Repository.MovieRepository
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel: ViewModel() {
    private val repository = MovieRepository.retrieve()
    var favoriteMovies: ArrayList<Movie> = ArrayList()
    var favoriteMoviesLiveData : MutableLiveData<List<Movie>>? = MutableLiveData()

    fun addFavoriteMovie(favoriteMovie: Movie) {
            favoriteMovies.add(favoriteMovie)
            favoriteMoviesLiveData?.value = favoriteMovies
            updateFavoriteMovieInDB(favoriteMovie)
    }

    fun removeFavoriteMovie(movieToRemove: Movie) {
        if(favoriteMovies.contains(movieToRemove)) {
            favoriteMovies.remove(movieToRemove)
            favoriteMoviesLiveData?.value = favoriteMovies
            removeFavoriteMovie(movieToRemove)
        }
    }

    private fun updateFavoriteMovieInDB(movie: Movie) {
        viewModelScope.launch {
            repository.addFavoriteMovieToDB(movie)
        }
    }

    private fun removeFavoriteMovieInDb(movie:Movie) {
        viewModelScope.launch {
            repository.removeFavoriteMovieFromDB(movie)
        }
    }
}