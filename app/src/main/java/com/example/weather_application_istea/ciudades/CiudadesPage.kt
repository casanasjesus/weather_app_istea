package com.example.weather_application_istea.ciudades

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.weather_application_istea.Ciudad
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CiudadesPage(
    navController: NavController,
    listaDeCiudades: List<Ciudad>
) {
    val viewModel = viewModel {
        CiudadesViewModel (
            navController = navController,
            listaDeCiudades = listaDeCiudades
        )
    }

    CiudadesView (
        estado = viewModel.estado
    ) { intencion ->
        viewModel.ejecutar(intencion)
    }
}