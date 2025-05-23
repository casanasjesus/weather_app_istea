package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit
) {
    var query by remember { mutableStateOf("") }

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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            BuscadorYGeoView(
                query = query,
                onQueryChange = {
                    query = it
                    onAction(CiudadesIntencion.BuscarCiudades(query))
                                },
                onGeolocalizacionClick = {
                    TODO()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            when(estado) {
                CiudadesEstado.Cargando -> CargandoCiudadesView()
                CiudadesEstado.Vacio -> VacioCiudadesView()
                is CiudadesEstado.Error -> ErrorCiudadesView()
                is CiudadesEstado.Resultado -> ResultadoCiudadesView(
                    modifier = Modifier,
                    estado.listaDeCiudades
                ) {
                    onAction(
                        CiudadesIntencion.CiudadSeleccionada(it)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CiudadesPreview() {}