package com.example.weather_application_istea.ciudades

import com.example.weather_application_istea.models.Ciudad

sealed class CiudadesEstado {
    data object Vacio: CiudadesEstado()
    data object Cargando: CiudadesEstado()
    data class Error(val error:String): CiudadesEstado()
    data class Resultado(val listaDeCiudades: List<Ciudad>): CiudadesEstado()
}