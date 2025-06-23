package com.example.weather_application_istea.repository

import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.models.ClimaActual
import com.example.weather_application_istea.models.Pronostico5Dias


interface Repository {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun getClimaActual(lat: Float, lon: Float): ClimaActual
    suspend fun getPronostico5Dias(lat: Float, lon: Float): Pronostico5Dias

}