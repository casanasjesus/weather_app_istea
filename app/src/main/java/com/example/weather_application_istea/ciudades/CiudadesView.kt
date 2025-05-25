package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit,
    onGeolocalizacionClick: () -> Unit   // ← lo nuevo
) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = { /* tu TopAppBar */ }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            BuscadorYGeoView(
                query               = query,
                onQueryChange       = { q ->
                    query = q
                    onAction(CiudadesIntencion.BuscarCiudades(q))
                },
                onGeolocalizacionClick = onGeolocalizacionClick  // ← aquí lo usas
            )

            Spacer(Modifier.height(8.dp))

            when (estado) {
                CiudadesEstado.Cargando -> CargandoCiudadesView()
                CiudadesEstado.Vacio   -> VacioCiudadesView()
                is CiudadesEstado.Error -> ErrorCiudadesView()
                is CiudadesEstado.Resultado -> ResultadoCiudadesView(
                    listaCiudades = estado.listaDeCiudades,
                    onSelect = { onAction(CiudadesIntencion.CiudadSeleccionada(it)) }
                )
            }
        }
    }
}
