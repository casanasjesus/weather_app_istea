package com.example.weather_application_istea.ciudades

import com.example.weather_application_istea.models.Ciudad

sealed class CiudadesIntencion {
    object CargarCiudades: CiudadesIntencion()
    class CiudadSeleccionada(val ciudad: Ciudad): CiudadesIntencion()
}