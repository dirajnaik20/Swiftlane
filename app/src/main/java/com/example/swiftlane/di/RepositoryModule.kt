package com.example.swiftlane.di

import com.example.swiftlane.data.remote.WeatherApiService
import com.example.swiftlane.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    private const val API_KEY = "2e070053535f414c8b3111116252310"


    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository {
        return WeatherRepository(api, API_KEY)
    }
}