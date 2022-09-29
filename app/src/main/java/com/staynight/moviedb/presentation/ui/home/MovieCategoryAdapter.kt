package com.staynight.moviedb.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.staynight.moviedb.databinding.ItemRvMoviesBinding
import com.staynight.moviedb.presentation.ui.models.MovieGroup

class MovieCategoryAdapter(private val listener: (id: Int, mediaType: String, addToWatchList: Boolean) -> Unit) : RecyclerView.Adapter<MovieCategoryAdapter.MovieGroupViewHolder>() {

    private var movies: List<MovieGroup> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGroupViewHolder {
        val view = ItemRvMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGroupViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun changeList(newMovies: List<MovieGroup>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieGroupViewHolder(private val item: ItemRvMoviesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(movieGroup: MovieGroup) = with(item) {
            val adapter = MoviesAdapter(){
                id, mediaType, addToWatchList ->
                listener.invoke(id, mediaType, addToWatchList)
            }
            adapter.changeList(movieGroup.movies)
            rvMoviesByCategories.adapter = adapter
            tvMovieCategoryTitle.text = movieGroup.title
        }
    }
}