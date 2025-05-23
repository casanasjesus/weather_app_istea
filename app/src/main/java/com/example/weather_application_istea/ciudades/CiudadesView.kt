package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weather_application_istea.models.Ciudad
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Weather App")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when(estado) {
                CiudadesEstado.Cargando -> CargandoCiudadesView()
                CiudadesEstado.Vacio -> VacioCiudadesView()
                is CiudadesEstado.Error -> ErrorCiudadesView()
                is CiudadesEstado.Resultado -> ResultadoCiudadesView(
                    listaCiudades = estado.listaDeCiudades,
                    onAction = onAction
                )
            }
        }
    }
}

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

@Composable
fun ResultadoCiudadesView(
    modifier: Modifier = Modifier,
    listaCiudades: List<Ciudad>,
    onAction: (CiudadesIntencion) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(15.dp)
    ) {
        items(listaCiudades) { ciudad ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onAction(CiudadesIntencion.CiudadSeleccionada(ciudad))
                        }
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("El listado de ciudades va acá")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CiudadesPreview() {}