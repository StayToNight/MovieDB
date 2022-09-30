package com.staynight.moviedb.presentation.ui.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.FragmentWatchlistBinding
import com.staynight.moviedb.utils.binding.BindingFragment
import javax.inject.Inject

class WatchlistFragment: BindingFragment<FragmentWatchlistBinding>(FragmentWatchlistBinding::inflate) {
    private val adapter = WatchlistAdapter(){id, mediaType, addToWatchList ->
        viewModel.addToWatchList(id, mediaType, addToWatchList)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<WatchlistViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MovieApp).appComponent?.injectWatchlistFragment(this)

        initViews()
        setupObservers()
        viewModel.getWatchList()
    }

    private fun initViews(){
        binding.apply {
            rvMovies.adapter = adapter
        }
    }

    private fun setupObservers(){
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            adapter.changeList(state)
        }
    }
}