package com.staynight.moviedb.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.staynight.moviedb.presentation.ui.auth.LoginField
import com.staynight.moviedb.presentation.ui.home.HomePage
import com.staynight.moviedb.presentation.ui.watchlist.WatchList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "auth") {
                composable("auth") {
                    LoginField(navController = navController)
                }
                composable("home") {
                    HomePage(navController = navController)
                }
                composable("watchlist") {
                    WatchList()
                }
            }
        }
    }

}