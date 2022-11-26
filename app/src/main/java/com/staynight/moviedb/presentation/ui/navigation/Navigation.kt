package com.staynight.moviedb.presentation.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.staynight.moviedb.presentation.ui.auth.LoginField
import com.staynight.moviedb.presentation.ui.details.DetailsScreen
import com.staynight.moviedb.presentation.ui.details.TestNavigationNested
import com.staynight.moviedb.presentation.ui.home.HomePage
import com.staynight.moviedb.presentation.ui.models.BottomNavItem
import com.staynight.moviedb.presentation.ui.watchlist.WatchList


@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.parent?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = { Text(text = item.badgeCount.toString()) },
                                content = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name
                                    )
                                })
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if (selected) {
                            Text(text = item.name, textAlign = TextAlign.Center, fontSize = 10.sp)
                        }
                    }
                })
        }
    }
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = "authBar") {
        navigation(startDestination = "auth", route = "authBar"){
            composable("auth") {
                LoginField(navController = navController)
            }
        }
        navigation(startDestination = "watchlist", route = "watchlistBar"){
            composable("watchlist") {
                WatchList()
            }
        }
        navigation(startDestination = "home", route = "homeBar", ){
            composable("home") {
                HomePage(navController = navController)
            }
            composable("details") {
                DetailsScreen(navController)
            }
            composable(route = "nestedDetails"){
                TestNavigationNested()
            }
        }
    }
}
