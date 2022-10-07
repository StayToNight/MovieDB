package com.staynight.moviedb.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.presentation.ui.Navigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(app = application as MovieApp)
        }
    }

}