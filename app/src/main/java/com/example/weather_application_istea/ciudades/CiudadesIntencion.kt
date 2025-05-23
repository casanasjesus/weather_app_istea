package com.example.weather_application_istea.ciudades

import com.example.weather_application_istea.models.Ciudad

sealed class CiudadesIntencion {
    data class BuscarCiudades(val nombreCiudad:String) : CiudadesIntencion()
    data class CiudadSeleccionada(val ciudad: Ciudad): CiudadesIntencion()
}