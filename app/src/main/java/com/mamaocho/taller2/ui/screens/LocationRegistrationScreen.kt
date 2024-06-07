package com.mamaocho.taller2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.mamaocho.taller2.MainViewModel

@Composable
fun LocationRegistrationScreen(viewModel: MainViewModel) {
    val location = viewModel.location.observeAsState()

    Column {
        Text(text = "Location")
        location.value?.let {
            Text(text = "Lat: ${it.first}, Lon: ${it.second}")
        }
    }
}
