package com.staynight.moviedb.presentation.ui.watchlist

import android.os.Bundle
import com.staynight.moviedb.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ramcosta.composedestinations.annotation.Destination
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.presentation.ui.home.MovieItem
import com.staynight.moviedb.presentation.ui.home.SubTitleText
import com.staynight.moviedb.presentation.ui.home.TitleText
import javax.inject.Inject

class WatchlistFragmentCompose: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<WatchlistViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WatchList(viewModel = viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // (activity?.application as MovieApp).appComponent?.injectWatchlistFragment(this)
    }
}

@Composable
fun WatchList(viewModel: WatchlistViewModel){
    Column(modifier = Modifier
        .background(colorResource(id = R.color.background_1))
        .padding(start = 29.dp)) {
        TitleText()
        SubTitleText(text = "Your Watch List")
        val state = viewModel.state
        val paginator = viewModel.paginator
        LazyColumn(Modifier.padding(vertical = 10.dp)){
            items(state.movies.size) { i ->
                val item = state.movies[i]
                if (i >= state.movies.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems(paginator)
                }
                MovieItem(movie = item, modifier = Modifier.padding(vertical = 10.dp))
            }
            item {
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}