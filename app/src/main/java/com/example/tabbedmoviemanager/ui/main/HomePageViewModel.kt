package com.example.tabbedmoviemanager.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabbedmoviemanager.Models.Configuration
import com.example.tabbedmoviemanager.Models.Genres
import com.example.tabbedmoviemanager.Models.Movie
import com.example.tabbedmoviemanager.Repository.MovieRepository
import kotlinx.coroutines.launch

class HomePageViewModel: ViewModel() {

    private val repository = MovieRepository.retrieve()
    var currentMovies : MutableLiveData<List<Movie>>? = null
    var popularMovies: MutableLiveData<List<Movie>>? = null
    var configurationData: MutableLiveData<Configuration>? = null
    var genreMap:  MutableLiveData<Genres>? = null
    private val isFavoritesTabSelected = MutableLiveData<Boolean>()
    val favoritesTab: LiveData<Boolean> get() = isFavoritesTabSelected

    init {
        getConfigurationData()
        getNowPlayingMovies("1")
        getPopularMovies("1")
        getGenres()
    }

    fun selectFavoritesTab() { isFavoritesTabSelected.value = true }

    private fun getPopularMovies(pageNumber: String) {
        viewModelScope.launch {
            val popularMoviesAPIData = repository.getPopularMoviesFromAPI(pageNumber)
            popularMovies = popularMoviesAPIData
        }
    }

    private fun getConfigurationData() {
        viewModelScope.launch{
            val configDataFromAPI = repository.getConfigurationDataFromAPI()
            configurationData = configDataFromAPI
        }
    }

    private fun getGenres() {
        viewModelScope.launch {
            val genresFromAPI = repository.getGenreMap()
            genreMap = genresFromAPI
        }
    }

    private fun getNowPlayingMovies(pageNumber: String)  {
        viewModelScope.launch {
            val currentMoviesAPIData = repository.getNowPlayingMoviesFromAPI(pageNumber)
            currentMovies = currentMoviesAPIData
        }
    }
}