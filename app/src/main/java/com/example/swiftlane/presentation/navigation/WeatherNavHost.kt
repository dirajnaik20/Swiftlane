package com.example.swiftlane.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.swiftlane.presentation.screens.SearchScreen
import com.example.swiftlane.presentation.screens.WeatherScreen
import kotlinx.serialization.Serializable


@Composable
fun WeatherNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SearchRoute) {
        composable<SearchRoute> {
            SearchScreen(
                onSearch = { city ->
                    navController.navigate(WeatherRoute(city = city))
                }
            )
        }
        composable<WeatherRoute> {
            val args = it.toRoute<WeatherRoute>()
            WeatherScreen(city = args.city)
        }
    }
}

@Serializable
data class WeatherRoute(
    val city: String
)

@Serializable
object SearchRoute