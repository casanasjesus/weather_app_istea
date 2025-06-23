package com.example.weather_application_istea.models

import kotlinx.serialization.Serializable

@Serializable
data class ClimaActual(
    val weather: List<WeatherDescription>,
    val main: MainInfo,
    val wind: WindInfo,
    val name: String,
    val sys: SysInfo? = null
)

@Serializable
data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)
@Serializable
data class Pronostico5Dias(
    val list: List<ForecastItem>
)

@Serializable
data class ForecastItem(
    val dt_txt: String,
    val main: MainInfo,
    val weather: List<WeatherDescription>,
    val wind: WindInfo
)

@Serializable
data class MainInfo(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class WindInfo(
    val speed: Float,
    val deg: Int
)

@Serializable
data class SysInfo(
    val country: String
)

