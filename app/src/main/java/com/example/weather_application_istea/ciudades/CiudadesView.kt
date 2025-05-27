package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit,
    onGeolocalizacionClick: () -> Unit,
    onUbicacionClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = { /* tu TopAppBar */ }
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                BuscadorYGeoView(
                    query = query,
                    onQueryChange = { q ->
                        query = q
                        onAction(CiudadesIntencion.BuscarCiudades(q))
                    },
                    onGeolocalizacionClick = onGeolocalizacionClick
                )

                Spacer(Modifier.height(8.dp))

                when (estado) {
                    CiudadesEstado.Cargando -> CargandoCiudadesView()
                    CiudadesEstado.Vacio -> VacioCiudadesView()
                    is CiudadesEstado.Error -> ErrorCiudadesView()
                    is CiudadesEstado.Resultado -> ResultadoCiudadesView(
                        listaCiudades = estado.listaDeCiudades,
                        onSelect = { onAction(CiudadesIntencion.CiudadSeleccionada(it)) }
                    )
                }
            }

            MiUbicacion(
                onUbicacionClick = onUbicacionClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}