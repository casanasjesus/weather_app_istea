package com.example.weather_application_istea.clima

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.Ciudad

@Composable
fun ClimaPage(
    navController: NavController,
    currentCiudad: MutableState<Ciudad?>
) {
    val ciudad = currentCiudad.value

    if (ciudad == null) {
        LaunchedEffect(Unit) {
            navController.navigate("ciudades") {
                popUpTo("clima") { inclusive = true }
            }
        }
        return
    }

    val viewModel = viewModel {
        ClimaViewModel (
            navController = navController,
            currentCiudad = currentCiudad
        )
    }

    ClimaView (
        estado = viewModel.estado
    ) { intencion ->
        viewModel.ejecutar(intencion)
    }
}