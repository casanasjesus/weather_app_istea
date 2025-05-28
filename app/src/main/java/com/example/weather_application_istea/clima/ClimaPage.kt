package com.example.weather_application_istea.clima

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ClimaPage(
    navController: NavController,
    lat: Float?,
    lon: Float?
) {
    // Si los argumentos llegan nulos, volvemos a la pantalla de ciudades
    if (lat == null || lon == null) {
        LaunchedEffect(Unit) {
            navController.navigate("ciudades") {
                popUpTo("clima/{lat}/{lon}") { inclusive = true }
            }
        }
        return
    }

    // Usamos factory para pasar los argumentos al ViewModel
    val viewModel: ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(lat, lon)
    )

    ClimaView(
        navController = navController,
        estado = viewModel.estado
    )
}
