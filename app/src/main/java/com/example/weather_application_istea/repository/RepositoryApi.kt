package com.example.weather_application_istea.repository

import com.example.weather_application_istea.models.Ciudad
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RepositoryApi: Repository {
    private val apiKey = "d3f5205f957e57d8e7bb15b9fef4a212"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys= true
            })
        }
    }

    override suspend fun buscarCiudad(ciudad: String): List<Ciudad> {
        val response = client.get("https://api.openweathermap.org/geo/1.0/direct") {
            parameter("q", ciudad)
            parameter("limit", 10)
            parameter("appid", apiKey)
        }

        if (response.status == HttpStatusCode.OK) {
            val ciudades = response.body<List<Ciudad>>()
            return ciudades
        } else {
            throw Exception()
        }
    }
}