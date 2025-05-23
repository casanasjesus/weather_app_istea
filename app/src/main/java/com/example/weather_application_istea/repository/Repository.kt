package com.example.weather_application_istea.repository

import com.example.weather_application_istea.models.Ciudad

interface Repository {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
}