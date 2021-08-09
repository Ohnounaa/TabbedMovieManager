package com.example.tabbedmoviemanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tabbedmoviemanager.Models.Genre
import com.example.tabbedmoviemanager.Models.Movie
import com.example.tabbedmoviemanager.R
import com.example.tabbedmoviemanager.databinding.FragmentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DetailFragment: BottomSheetDialogFragment() {

    private val imageUrlStem = "https://image.tmdb.org/t/p/w500/"
    lateinit var fragmentLayout: View

    private val homePageViewModel: HomePageViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
    }
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavoriteMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        fragmentLayout = binding.root
        val genres: MutableList<Genre> = mutableListOf()
        homePageViewModel.genreMap?.observe(viewLifecycleOwner, {genresList ->
            genresList.genres.forEach { genres?.add(it) }
            detailViewModel.selectedMovie.observe( viewLifecycleOwner, { movie ->
                createDetailView(movie, binding, genres) })
        })
        return fragmentLayout
    }

    private fun createDetailView(
        movie: Movie,
        binding: FragmentDetailBinding,
        genresNamesList: MutableList<Genre>?
    ) {
        binding.movieViewModel = detailViewModel
        binding.apply {
            movieViewModel?.setTitle(movie.title)
            movieViewModel?.setOverview(movie.overview)
            movieViewModel?.setReleaseDate(movie.release_date)
            movieViewModel?.setRating(movie.vote_average)
            movieViewModel?.setGenres(movie.genre_ids, genresNamesList)
            loadImage(movieImage, imageUrlStem + movie.poster_path)
            if(favoriteMoviesViewModel.favoriteMovies.contains(movie)) {
                favoriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
            favoriteButton.setOnClickListener {
                if(!favoriteMoviesViewModel.favoriteMovies.contains(movie)) {
                    favoriteMoviesViewModel.addFavoriteMovie(movie)
                   favoriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    favoriteMoviesViewModel.removeFavoriteMovie(movie)
                    favoriteButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
    }

    companion object {
        fun newInstance(
        ) : DetailFragment {
            Bundle().apply{}
            return DetailFragment()
        }
    }
}