package com.staynight.moviedb.presentation.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.staynight.moviedb.R
import com.staynight.moviedb.domain.models.Movie
import com.staynight.moviedb.domain.models.Movies
import com.staynight.moviedb.utils.helpers.Paginator

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.background_1))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(start = 29.dp)
        ) {
            TitleText()
            SubTitleText(text = "Find your movies")
            var searchText by remember { mutableStateOf("") }

            Box(modifier = Modifier) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(id = R.color.background_3))
                        .border(color = Color.Black, width = 1.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp
                    )

                )
            }
            HomeRVAnother(
                viewModel,
                viewModel.topRatedState,
                viewModel.paginatorTopRated,
                Modifier.padding(top = 50.dp)
            ) { navController.navigate("details") }
            HomeRVAnother(
                viewModel,
                viewModel.popularState,
                viewModel.paginatorPopular
            ) { navController.navigate("details") }
            HomeRVAnother(
                viewModel,
                viewModel.upcomingState,
                viewModel.paginatorUpcoming
            ) { navController.navigate("details") }
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("watchlist")
            },
            backgroundColor = colorResource(id = R.color.background_3),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 29.dp, bottom = 31.dp)
                .width(134.dp)
        ) {
            Text(text = "Watch List")
        }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "Movie DB App", style = TextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight(700),
            color = colorResource(id = R.color.text),
        ),
        modifier = Modifier
            .padding(top = 50.dp)
    )
}

@Composable
fun SubTitleText(text: String) {
    Text(
        text = text, style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            color = colorResource(id = R.color.text),
        ),
        modifier = Modifier
            .padding(top = 22.dp)
    )
}

@Composable
fun HomeRVAnother(
    viewModel: HomeViewModel,
    state: HomeViewModel.State,
    paginator: Paginator<Int, Movies>,
    modifier: Modifier = Modifier,
    detailClick: () -> Unit
) {
    val stateList = rememberLazyListState()
    Column(modifier = modifier) {
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
                MovieItem(movie = item) { detailClick.invoke() }
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
fun MovieItem(movie: Movie, modifier: Modifier = Modifier, detailsClick: () -> Unit) {
    Row(modifier = modifier
        .padding(end = 15.dp)
        .clickable { detailsClick.invoke() }) {
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