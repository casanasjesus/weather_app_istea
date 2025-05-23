package com.example.weather_application_istea.models

data class Ciudad (
    val name: String,
    val lat: Float,
    val lon: Float,
    val country: String,
    val state: String = ""
)