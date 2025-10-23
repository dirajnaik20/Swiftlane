package com.example.swiftlane.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.swiftlane.presentation.components.CenteredText
import com.example.swiftlane.viewmodel.UiState
import com.example.swiftlane.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    city: String,
    viewModel: WeatherViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    LaunchedEffect(city) { if (city.isNotBlank()) viewModel.searchCity(city) }
    val state by viewModel.uiState.collectAsState()


    when (state) {
        is UiState.Idle -> CenteredText("Enter a city to search")
        is UiState.Loading -> CenteredText("Loading...")
        is UiState.Error -> CenteredText((state as UiState.Error).message)
        is UiState.Success -> {
            val data = (state as UiState.Success).data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${data.location.name}, ${data.location.country}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Image(
                    painter = rememberAsyncImagePainter("https:${data.current.condition.icon}"),
                    contentDescription = data.current.condition.text,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "${data.current.temp_c}°C",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = data.current.condition.text,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Humidity: ${data.current.humidity}%")
                    Text("Wind: ${data.current.wind_kph} kph")
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Latitude: %.4f°".format(data.location.lat))
                    Text(text = "Longitude: %.4f°".format(data.location.lon))
                }
            }
        }
    }
}