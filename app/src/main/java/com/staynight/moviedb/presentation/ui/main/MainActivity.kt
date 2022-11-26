package com.staynight.moviedb.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.staynight.moviedb.presentation.ui.models.BottomNavItem
import com.staynight.moviedb.presentation.ui.navigation.BottomNavigationBar
import com.staynight.moviedb.presentation.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(items = listOf(
                        BottomNavItem(
                            name = "Auth",
                            route = "authBar",
                            icon = Icons.Default.Star,
                        ),
                        BottomNavItem(
                            name = "Home",
                            route = "homeBar",
                            icon = Icons.Default.Home,
                        ),
                        BottomNavItem(
                            name = "Watchlist",
                            route = "watchlistBar",
                            icon = Icons.Default.List,
                            badgeCount = 11
                        )
                    ), navController = navController, onClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
            ) {
                Navigation(navController = navController)
            }
        }
    }

}