package com.staynight.moviedb.presentation.ui.home

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.staynight.moviedb.databinding.ItemRvMoviesBinding
import com.staynight.moviedb.domain.models.MovieGroup

class MovieCategoryAdapter(
    private val addToWatchListListener: (id: Int, mediaType: String, addToWatchList: Boolean) -> Unit,
    private val newMoviesListener : (id: Int, rv: RecyclerView) -> Unit
) : RecyclerView.Adapter<MovieCategoryAdapter.MovieGroupViewHolder>() {

    private var movies: List<MovieGroup> = listOf()
    val rvs: MutableList<RecyclerView> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGroupViewHolder {
        val view = ItemRvMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGroupViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun getMovies() = movies

    fun changeList(newMovies: List<MovieGroup>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieGroupViewHolder(private val item: ItemRvMoviesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(movieGroup: MovieGroup) = with(item) {
            rvs.add(rvMoviesByCategories)
            fun createScrollListener(){
                rvMoviesByCategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollHorizontally(1)) {
                            newMoviesListener.invoke(adapterPosition, recyclerView)
                            rvMoviesByCategories.clearOnScrollListeners()
                            Handler().postDelayed(
                                {createScrollListener()},
                                2000
                            )
                        }
                    }
                })
            }
            val adapter = MoviesAdapter() { id, mediaType, addToWatchList ->
                addToWatchListListener.invoke(id, mediaType, addToWatchList)
            }
            adapter.changeList(movieGroup.movies)
            rvMoviesByCategories.adapter = adapter
            tvMovieCategoryTitle.text = movieGroup.title
            rvMoviesByCategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollHorizontally(1)) {
                        newMoviesListener.invoke(adapterPosition, recyclerView)
                        rvMoviesByCategories.clearOnScrollListeners()
                        Handler().postDelayed(
                            {createScrollListener()},
                            2000
                        )
                    }
                }
            })
        }
    }
}