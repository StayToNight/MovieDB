package com.staynight.moviedb.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
    private val adapter = MovieCategoryAdapter(){ id, mediaType, addToWatchList ->
        viewModel.addToWatchList(id, mediaType, addToWatchList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as MovieApp).appComponent?.injectHomeFragment(this)

        binding.rvMovies.adapter = adapter

        setupObservers()
        setupListeners()

        viewModel.getTopRatedMovies()

    }

    private fun setupObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeViewModel.State.Movies -> {
                    adapter.changeList(state.movies)
                }
            }
        }
    }

    private fun setupListeners(){
        binding.apply {
            btnWatchlist.setOnClickListener {
                navigateTo(WatchlistFragment(), parentFragmentManager)
            }
        }
    }
}