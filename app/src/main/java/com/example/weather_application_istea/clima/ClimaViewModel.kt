package com.example.weather_application_istea.clima

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.models.Ciudad

class ClimaViewModel(
    val navController: NavController? = null,
    val currentCiudad: MutableState<Ciudad?>
): ViewModel() {
    val estado by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) {
        when(intencion) {
            TODO() -> {

            }
        }
    }
}