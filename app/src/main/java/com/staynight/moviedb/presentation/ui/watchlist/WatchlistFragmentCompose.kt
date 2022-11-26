package com.staynight.moviedb.presentation.ui.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.staynight.moviedb.R
import com.staynight.moviedb.presentation.ui.home.MovieItem
import com.staynight.moviedb.presentation.ui.home.SubTitleText
import com.staynight.moviedb.presentation.ui.home.TitleText


@Composable
fun WatchList(viewModel: WatchlistViewModel = hiltViewModel()){
    Column(modifier = Modifier
        .fillMaxSize()
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
                MovieItem(movie = item, modifier = Modifier.padding(vertical = 10.dp)){}
            }
            item {
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}