package com.example.weather_application_istea.clima

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_application_istea.repository.RepositoryApi
import kotlinx.coroutines.launch

class ClimaViewModel(
    private val lat: Float,
    private val lon: Float
) : ViewModel() {

    var estado by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)
        private set

    private val repository = RepositoryApi()

    init {
        cargarClima()
    }

    private fun cargarClima() {
        estado = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val clima = repository.getClimaActual(lat, lon)
                estado = ClimaEstado.Resultado(clima)
            } catch (e: Exception) {
                estado = ClimaEstado.Error(error = e.message ?: "Error desconocido")
            }
        }
    }
}
