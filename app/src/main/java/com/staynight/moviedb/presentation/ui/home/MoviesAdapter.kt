package com.staynight.moviedb.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.staynight.moviedb.R
import com.staynight.moviedb.databinding.ItemMovieBinding
import com.staynight.moviedb.domain.models.Movie

class MoviesAdapter(private val listener: (id: Int, mediaType: String, addToWatchList: Boolean) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    private var movies: List<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun changeList(newMovies: List<Movie>) {
        movies = newMovies.toMutableList()
        notifyDataSetChanged()
    }

    fun getMovies() = movies

    inner class MoviesViewHolder(private val item: ItemMovieBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(movieData: Movie) = with(item) {
            tvMovieTitle.text = movieData.title
            tvMovieDate.text = movieData.releaseDate
            tvMovieRate.text = movieData.voteAverage.toString()
            ivMovie.load(IMAGE_URL + movieData.posterPath) {
                crossfade(true)
                placeholder(R.drawable.background_buttons)
                transformations(RoundedCornersTransformation(16.toFloat()))
                error(com.google.android.material.R.drawable.mtrl_ic_error)
            }
            tbAddToWatchlist.isChecked = movieData.atWatchlist
            tbAddToWatchlist.setOnClickListener {
                if (movieData.id != null) {
                    listener.invoke(movieData.id, "movie", tbAddToWatchlist.isChecked)
                    movieData.atWatchlist = tbAddToWatchlist.isChecked
                }
            }
        }
    }
}