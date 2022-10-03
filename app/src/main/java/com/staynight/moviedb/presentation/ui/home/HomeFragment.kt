package com.staynight.moviedb.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.FragmentHomeBinding
import com.staynight.moviedb.presentation.ui.watchlist.WatchlistFragment
import com.staynight.moviedb.utils.binding.BindingFragment
import com.staynight.moviedb.utils.extensions.navigateTo
import javax.inject.Inject

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private var currentRv: RecyclerView? = null
    private var currentLManager: LayoutManager? = null
    private val adapter = MovieCategoryAdapter({ id, mediaType, addToWatchList ->
        viewModel.addToWatchList(id, mediaType, addToWatchList)
    }, { id, rv ->
        when (id) {
            0 -> {
                viewModel.getTopRatedMovies(0)
                currentRv = rv
                currentLManager = currentRv?.layoutManager
            }
            1 -> {
                viewModel.getPopularMovies(1)
                currentRv = rv
                currentLManager = currentRv?.layoutManager
            }
            2 -> {
                viewModel.getUpcomingMovies(2)
                currentRv = rv
                currentLManager = currentRv?.layoutManager
            }
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as MovieApp).appComponent?.injectHomeFragment(this)

        binding.rvMovies.adapter = adapter

        setupObservers()
        setupListeners()

        viewModel.getHomePage()

    }

    private fun setupObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeViewModel.State.Movies -> {
                    adapter.changeList(state.movies)
                }
                is HomeViewModel.State.NewMovies -> {
                    when (state.id) {
                        0 -> {
                            adapter.getMovies()[0].movies.addAll(state.movies)
                            adapter.notifyDataSetChanged()
                            currentRv?.scrollToPosition((currentRv?.adapter as MoviesAdapter).getMovies().size - 1)
                        }
                        1 -> {
                            adapter.getMovies()[1].movies.addAll(state.movies)
                            adapter.notifyDataSetChanged()
                            currentRv?.scrollToPosition((currentRv?.adapter as MoviesAdapter).getMovies().size)
                        }
                        2 -> {
                            adapter.getMovies()[2].movies.addAll(state.movies)
                            adapter.notifyDataSetChanged()
                            currentRv?.scrollToPosition((currentRv?.adapter as MoviesAdapter).getMovies().size)
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            btnWatchlist.setOnClickListener {
                navigateTo(WatchlistFragment(), parentFragmentManager)
            }
        }
    }
}