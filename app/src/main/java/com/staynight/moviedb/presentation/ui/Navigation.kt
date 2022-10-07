package com.staynight.moviedb.presentation.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.presentation.ui.auth.LoginField
import com.staynight.moviedb.presentation.ui.home.HomePage

@Composable
fun Navigation(app: MovieApp){
    val navController = rememberNavController()

    NavHost(navController, startDestination = "NavigationDestination.Screen1.destination") {
        composable("NavigationDestination.Screen1.destination") {
            Log.e("TAG", "onCreate: login", )
            LoginField(app.appComponent?.getAuthViewModel()!!, navController)
        }
        composable("NavigationDestination.Screen2.destination") {
            Log.e("TAG", "onCreate: home", )
            HomePage(app.appComponent?.getHomeViewModel()!!)
        }
    }
}