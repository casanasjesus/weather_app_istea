package com.example.weather_application_istea.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.Ciudad

class CiudadesViewModel(
    val navController: NavController? = null,
    val listaDeCiudades: List<Ciudad>,
): ViewModel() {
    val estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)

    fun ejecutar(intencion: CiudadesIntencion) {
        when(intencion) {
            TODO() -> {

            }
        }
    }
}