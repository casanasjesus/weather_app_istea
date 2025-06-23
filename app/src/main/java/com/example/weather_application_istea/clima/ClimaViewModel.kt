package com.example.weather_application_istea.clima

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_application_istea.repository.RepositoryApi
import kotlinx.coroutines.launch
import com.example.weather_application_istea.models.ForecastItem

class ClimaViewModel(
    private val lat: Float,
    private val lon: Float
) : ViewModel() {

    var estado by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)
        private set
    var extremosPorDia by mutableStateOf<List<Triple<String, Float, Float>>>(emptyList())
        private set

    private val repository = RepositoryApi()

    init {
        cargarClima()
        cargarPronostico5Dias()
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
    private fun cargarPronostico5Dias() {
        viewModelScope.launch {
            try {
                val pronostico = repository.getPronostico5Dias(lat, lon)
                print(pronostico)
                extremosPorDia = obtenerExtremosPorDia(pronostico.list)
            } catch (e: Exception) {
                println("Error cargando pronóstico: ${e.message}")
            }
        }
    }

    private fun obtenerExtremosPorDia(forecast: List<ForecastItem>): List<Triple<String, Float, Float>> {
        return forecast
            .groupBy { it.dt_txt.substring(0, 10) } // Agrupar por fecha YYYY-MM-DD
            .map { (fecha, itemsDelDia) ->
                val min = itemsDelDia.minOf { it.main.temp_min }
                val max = itemsDelDia.maxOf { it.main.temp_max }
                Triple(fecha.substring(5), min, max) // Devolver MM-DD
            }
            .take(5)
    }


}
