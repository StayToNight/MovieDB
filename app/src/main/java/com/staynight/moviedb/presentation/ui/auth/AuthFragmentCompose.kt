package com.staynight.moviedb.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.staynight.moviedb.MovieApp
import com.staynight.moviedb.R
import com.staynight.moviedb.presentation.ui.home.HomeFragmentCompose
import com.staynight.moviedb.utils.extensions.navigateTo
import javax.inject.Inject

class AuthFragmentCompose : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginField(viewModel = viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MovieApp).appComponent?.injectAuthFragment(this)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthViewModel.State.Success -> {
                    Toast.makeText(context, "Authorized", Toast.LENGTH_LONG).show()
                    navigateTo(HomeFragmentCompose(), parentFragmentManager)
                }
            }
        }
    }
}

@Composable
fun LoginField(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.background_1))
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var username by remember { mutableStateOf("StayToNight") }
        var password by remember { mutableStateOf("nurtas123") }
        CustomTextField(text = username, onChange = { username = it })
        CustomTextField(text = password, onChange = { password = it })
        CustomButton(loadingState = viewModel.buttonLoadingState, authClick = {
            viewModel.authWithLogin(username, password)
        })
    }
}

@Composable
fun CustomTextField(modifier: Modifier = Modifier, text: String, onChange: (String) -> Unit) {
    TextField(
        value = text, onValueChange = { onChange.invoke(it) }, modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp, vertical = 15.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorResource(id = R.color.background_3)),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CustomButton(authClick: () -> Unit, loadingState: Boolean) {
    Button(
        onClick = authClick,
        modifier = Modifier
            .width(220.dp)
            .height(100.dp)
            .padding(horizontal = 35.dp)
            .padding(top = 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.background_2))
    ) {
        if (loadingState) {
            CircularProgressIndicator()
        } else {
            Text(text = "LOGIN", style = TextStyle(color = Color.White))
        }
    }
}