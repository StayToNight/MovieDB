package com.staynight.moviedb.presentation.ui.auth

import android.util.Log
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.staynight.moviedb.R

@Composable
fun LoginField (
    viewModel: AuthViewModel,
    navController: NavController
) {
    Log.e("TAG", viewModel.toString() )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.background_1))
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var username by remember { mutableStateOf("StayToNight") }
        var password by remember { mutableStateOf("nurtas123") }
        CustomTextField(text = username, onChange = { username = it })
        CustomTextField(text = password, onChange = { password = it })
        CustomButton(viewModel = viewModel, authClick = {
            viewModel.authWithLogin(username, password)
        })
    }
    if(viewModel.loginSuccess){
        navController.navigate("NavigationDestination.Screen2.destination")
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
fun CustomButton(authClick: () -> Unit, viewModel: AuthViewModel) {
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
        if (viewModel.buttonLoadingState) {
            CircularProgressIndicator()
        } else {
            Text(text = "LOGIN", style = TextStyle(color = Color.White))
        }
    }
}