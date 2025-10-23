package com.example.swiftlane.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftlane.data.remote.models.WeatherResponse
import com.example.swiftlane.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface UiState {
    object Idle : UiState
    object Loading : UiState
    data class Success(val data: WeatherResponse) : UiState
    data class Error(val message: String) : UiState
}


@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun searchCity(city: String) {
        if (city.isBlank()) return
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repo.fetchCurrentWeather(city.trim())
            result.fold(
                onSuccess = { _uiState.value = UiState.Success(it) },
                onFailure = { _uiState.value = UiState.Error(it.localizedMessage ?: "Unknown") }
            )
        }
    }
}