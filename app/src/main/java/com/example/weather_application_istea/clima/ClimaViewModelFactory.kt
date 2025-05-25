package com.example.weather_application_istea.clima

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClimaViewModelFactory(
    private val lat: Float,
    private val lon: Float
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClimaViewModel(lat, lon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
