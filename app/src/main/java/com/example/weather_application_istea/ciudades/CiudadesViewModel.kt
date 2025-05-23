package com.example.weather_application_istea.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.Ciudad

class CiudadesViewModel(
    val navController: NavController? = null,
    val listaDeCiudades: List<Ciudad>,
): ViewModel() {
    var estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)

    fun ejecutar(intencion: CiudadesIntencion) {
        when(intencion) {
            CiudadesIntencion.CargarCiudades -> cargarCiudades()
            is CiudadesIntencion.CiudadSeleccionada -> ciudadSeleccionada(intencion.ciudad)
        }
    }

    fun cargarCiudades() {
        estado = CiudadesEstado.Resultado(listaDeCiudades)
    }

    fun ciudadSeleccionada(ciudad: Ciudad) {
        navController?.navigate("clima/${ciudad.nombre}")
    }
}