package com.example.weather_application_istea.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.repository.Repository
import kotlinx.coroutines.launch

class CiudadesViewModel(
    val navController: NavController? = null,
    val listaDeCiudades: List<Ciudad>,
    val repository: Repository
): ViewModel() {
    var estado by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)
    var ciudades : List<Ciudad> = emptyList()

    fun ejecutar(intencion: CiudadesIntencion) {
        when(intencion) {
            is CiudadesIntencion.BuscarCiudades -> buscarCiudades(nombreCiudad = intencion.nombreCiudad)
            is CiudadesIntencion.CiudadSeleccionada -> ciudadSeleccionada(intencion.ciudad)
        }
    }

    fun buscarCiudades(nombreCiudad: String) {
        estado = CiudadesEstado.Cargando

        viewModelScope.launch {
            try {
                ciudades = repository.buscarCiudad(nombreCiudad)

                if(ciudades.isEmpty()) {
                    estado = CiudadesEstado.Vacio
                } else {
                    estado = CiudadesEstado.Resultado(ciudades)
                }
            } catch (exeption: Exception) {
                estado = CiudadesEstado.Error(exeption.message ?: "Error desconocido")
            }
        }
    }

    fun ciudadSeleccionada(ciudad: Ciudad) {
        navController?.navigate("clima/${ciudad.name}")
    }
}