package com.example.weather_application_istea.clima

sealed class ClimaEstado {
    data object Vacio: ClimaEstado()
    data object Cargando: ClimaEstado()
    data class Error(val error:String): ClimaEstado()
    data class Resultado(val clima: List<Any>): ClimaEstado()
}