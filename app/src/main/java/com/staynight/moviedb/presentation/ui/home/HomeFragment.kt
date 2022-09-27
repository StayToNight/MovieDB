package com.staynight.moviedb.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.FragmentHomeBinding
import com.staynight.moviedb.utils.binding.BindingFragment
import javax.inject.Inject

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val adapter = MoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.application as MovieApp).appComponent.injectHomeFragment(this)

        binding.rvMovies.adapter = adapter

        setupObservers()

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
}