package com.example.swiftlane.data.repository

import com.example.swiftlane.data.remote.WeatherApiService
import com.example.swiftlane.data.remote.models.WeatherResponse

class WeatherRepository(private val api: WeatherApiService, private val apiKey: String) {
    suspend fun fetchCurrentWeather(city: String): Result<WeatherResponse> {
        return try {
            val resp = api.getCurrentWeather(apiKey, city)
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}