package com.staynight.moviedb.presentation.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.databinding.FragmentAuthBinding
import com.staynight.moviedb.presentation.ui.home.HomeFragment
import com.staynight.moviedb.utils.binding.BindingFragment
import com.staynight.moviedb.utils.extensions.makeGone
import com.staynight.moviedb.utils.extensions.makeVisible
import com.staynight.moviedb.utils.extensions.navigateTo
import javax.inject.Inject

class AuthFragment: BindingFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel>{viewModelFactory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MovieApp).appComponent?.injectAuthFragment(this)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners(){
        binding.apply {
            btnLogin.setOnClickListener{
                viewModel.authWithLogin(login = etLogin.text.toString(), password = etPassword.text.toString())
            }
        }
    }

    private fun setupObservers(){
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthViewModel.State.Error -> {
                    binding.apply {
                        btnLogin.makeVisible()
                        pb.makeGone()
                    }
                }
                AuthViewModel.State.HideLoading -> {
                    binding.apply {
                        pb.makeGone()
                    }
                }
                AuthViewModel.State.ShowLoading -> {
                    binding.apply {
                        pb.makeVisible()
                        btnLogin.makeGone()
                    }
                }
                AuthViewModel.State.Success -> {
                    Toast.makeText(context, "Authorized", Toast.LENGTH_LONG).show()
                    navigateTo(HomeFragment(), parentFragmentManager)
                }
            }
        }
    }
}