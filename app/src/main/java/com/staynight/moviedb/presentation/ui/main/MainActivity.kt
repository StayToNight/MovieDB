package com.staynight.moviedb.presentation.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.ActivityMainBinding
import com.staynight.moviedb.presentation.ui.home.HomeFragment
import com.staynight.moviedb.utils.binding.BindingActivity
import com.staynight.moviedb.utils.extensions.navigateTo
import javax.inject.Inject

class MainActivity : BindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel>{viewModelFactory}

    val TAG = "DAGGERTEST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navigateTo(HomeFragment(), supportFragmentManager)

        (application as MovieApp).appComponent.testInject(this)

        setupObservers()

        viewModel.getRequestToken()


    }

    private fun setupObservers(){
        viewModel.liveData.observe(this) { state ->
            Log.e(TAG, "setupObservers: $state")
        }
    }
}