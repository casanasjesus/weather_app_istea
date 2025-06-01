package com.example.weather_application_istea.repository

import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.models.ClimaActual

interface Repository {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun getClimaActual(lat: Float, lon: Float): ClimaActual
}