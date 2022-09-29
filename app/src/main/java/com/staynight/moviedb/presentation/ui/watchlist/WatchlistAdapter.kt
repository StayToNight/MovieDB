package com.staynight.moviedb.presentation.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.staynight.moviedb.data.models.Movie
import com.staynight.moviedb.databinding.ItemWatchlistBinding

class WatchlistAdapter(private val listener: (id: Int, mediaType: String, addToWatchList: Boolean) -> Unit) :
    RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val view = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun changeList(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class WatchlistViewHolder(private val item: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(movie: Movie) = with(item) {
            tvMovieTitle.text = movie.title
            tvMovieDate.text = movie.release_date
            tvMovieRate.text = movie.vote_average.toString()
            ivMovie.load(IMAGE_URL + movie.poster_path) {
                crossfade(true)
            }
            tbAddToWatchlist.isChecked = movie.atWatchlist
            tbAddToWatchlist.setOnClickListener {
                if (movie.id != null) {
                    listener.invoke(movie.id, "movie", tbAddToWatchlist.isChecked)
                    movie.atWatchlist = tbAddToWatchlist.isChecked
                }
            }
        }
    }
}