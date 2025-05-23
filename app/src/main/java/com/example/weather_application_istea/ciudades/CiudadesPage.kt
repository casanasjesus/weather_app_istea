package com.example.weather_application_istea.ciudades

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.repository.RepositoryApi

@Composable
fun CiudadesPage(
    navController: NavController,
    listaDeCiudades: List<Ciudad>
) {
    val viewModel: CiudadesViewModel = viewModel {
        CiudadesViewModel (
            navController = navController,
            listaDeCiudades = listaDeCiudades,
            repository = RepositoryApi()
        )
    }

    CiudadesView (
        estado = viewModel.estado,
        onAction = { intencion: CiudadesIntencion ->
            viewModel.ejecutar(intencion)
        }
    )
}