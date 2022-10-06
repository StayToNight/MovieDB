package com.staynight.moviedb.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.FragmentHomeBinding
import com.staynight.moviedb.domain.models.Movie
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.presentation.ui.watchlist.WatchlistFragment
import com.staynight.moviedb.utils.binding.BindingFragment
import com.staynight.moviedb.utils.extensions.navigateTo
import com.staynight.moviedb.utils.helpers.Paginator
import javax.inject.Inject

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

class HomeFragment : BindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MovieApp).appComponent?.injectHomeFragment(this)
        setupListeners()

        binding.myComposable.setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                HomeRVAnother(viewModel, viewModel.topRatedState, viewModel.paginatorTopRated)
                HomeRVAnother(viewModel, viewModel.popularState, viewModel.paginatorPopular)
                HomeRVAnother(viewModel, viewModel.upcomingState, viewModel.paginatorUpcoming)
            }
        }
    }

    private fun setupObservers() {

    }

    private fun setupListeners() {
        binding.apply {
            btnWatchlist.setOnClickListener {
                navigateTo(WatchlistFragment(), parentFragmentManager)
            }
        }
    }
}

@Composable
fun HomeRVAnother(
    viewModel: HomeViewModel,
    state: HomeViewModel.State,
    paginator: Paginator<Int, Movies>
) {
    val stateList = rememberLazyListState()
    Column {
        Text(
            text = state.title,
            style = TextStyle(fontWeight = FontWeight(700), color = Color.White, fontSize = 18.sp)
        )
        LazyRow(modifier = Modifier.padding(vertical = 10.dp), state = stateList) {
            items(state.movies.size) { i ->
                val item = state.movies[i]
                if (i >= state.movies.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems(paginator)
                }
                MovieItem(movie = item)
            }
            item {
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(end = 15.dp)) {
        AsyncImage(
            model = IMAGE_URL + movie.posterPath,
            contentDescription = "",
            modifier = Modifier
                .height(140.dp)
                .padding(end = 15.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column {
            Text(
                text = "Title",
                style = TextStyle(color = Color.White, fontWeight = FontWeight(700))
            )
            Text(
                text = movie.title.toString(),
                style = TextStyle(color = Color.White, fontWeight = FontWeight(400))
            )
            Text(
                text = "Release date",
                style = TextStyle(color = Color.White, fontWeight = FontWeight(700))
            )
            Text(
                text = movie.releaseDate.toString(),
                style = TextStyle(color = Color.White, fontWeight = FontWeight(400))
            )
            Text(
                text = "Rating",
                style = TextStyle(color = Color.White, fontWeight = FontWeight(700))
            )
            Text(
                text = movie.voteAverage.toString(),
                style = TextStyle(color = Color.White, fontWeight = FontWeight(400))
            )
        }
    }
}
