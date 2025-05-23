package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MessageBoxView(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun CargandoCiudadesView() {
    MessageBoxView(message = "Cargando lista de ciudades")
}

@Composable
fun VacioCiudadesView() {
    MessageBoxView(message = "No hay ciudades para  mostrar")
}

@Composable
fun ErrorCiudadesView() {
    MessageBoxView(message = "Ocurrió un error al cargar las ciudades")
}