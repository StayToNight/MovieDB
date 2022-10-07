package com.staynight.moviedb.presentation.ui.main

import android.os.Bundle
import com.staynight.moviedb.databinding.ActivityMainBinding
import com.staynight.moviedb.presentation.ui.auth.AuthFragmentCompose
import com.staynight.moviedb.utils.binding.BindingActivity
import com.staynight.moviedb.utils.extensions.navigateTo

class MainActivity : BindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navigateTo(AuthFragmentCompose(), supportFragmentManager)
    }

}