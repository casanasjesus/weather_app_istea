package com.example.weather_application_istea.models

import kotlinx.serialization.Serializable

@Serializable
data class Ciudad(
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String? = null // Puede ser nulo según la API
)
