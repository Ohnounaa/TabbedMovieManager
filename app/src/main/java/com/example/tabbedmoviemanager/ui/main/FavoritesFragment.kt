package com.example.tabbedmoviemanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabbedmoviemanager.Models.Movie
import com.example.tabbedmoviemanager.R
import com.example.tabbedmoviemanager.databinding.FavoriteMovieViewHolderBinding
import com.example.tabbedmoviemanager.databinding.FragmentFavoritesBinding


class FavoritesFragment: Fragment() {

    val favoriteMoviesViewModel: FavoriteMoviesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavoriteMoviesViewModel::class.java)
    }

    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }

    private val imageUrlStem = "https://image.tmdb.org/t/p/w500/"
    lateinit var fragmentLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites,
            container,
            false
        )
        fragmentLayout = binding.root


        favoriteMoviesViewModel.favoriteMoviesLiveData?.observe(
            viewLifecycleOwner, {movies ->
                binding.favoriteMoviesCollection.apply{
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    adapter = FavoriteMovieAdapter(movies)
                }

            }
        )
        return fragmentLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    inner class FavoriteMovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<FavoriteMovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
            val binding = DataBindingUtil.inflate<FavoriteMovieViewHolderBinding>(
                layoutInflater, R.layout.favorite_movie_view_holder, parent, false
            )
            return FavoriteMovieViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
            holder.bind(movies[position])
        }

        override fun getItemCount(): Int {
            return movies.size
        }
    }

    inner class FavoriteMovieViewHolder(private val binding: FavoriteMovieViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                binding.favoriteMovieTitle.text = movie.title
                loadImage(binding.movieImage, imageUrlStem + movie.poster_path)
                removeButton.setOnClickListener {
                    favoriteMoviesViewModel.removeFavoriteMovie(movie)
                }
                movieImage.setOnClickListener{ detailViewModel.selectMovie(movie) }
                executePendingBindings()
            }
        }
    }

    companion object {
        fun newInstance(
        ) : FavoritesFragment {
            val args = Bundle().apply{}
            return FavoritesFragment().apply {  arguments = args }
        }
    }

}