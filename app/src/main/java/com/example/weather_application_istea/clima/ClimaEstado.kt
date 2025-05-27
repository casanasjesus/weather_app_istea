package com.example.weather_application_istea.clima

import com.example.weather_application_istea.models.ClimaActual

sealed class ClimaEstado {
    data object Vacio : ClimaEstado()
    data object Cargando : ClimaEstado()
    data class Error(val error: String) : ClimaEstado()
    data class Resultado(val clima: ClimaActual) : ClimaEstado()
}
