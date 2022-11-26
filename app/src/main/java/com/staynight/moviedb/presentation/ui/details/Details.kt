package com.staynight.moviedb.presentation.ui.details

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailsScreen(navController: NavController){
    Button(onClick = { navController.navigate("nestedDetails") }) {
        Text(text = "Details")
    }

}

@Composable
fun TestNavigationNested(){
    Text(text = "Nested navigation")
}