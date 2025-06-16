package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesView(
    estado: CiudadesEstado,
    onAction: (CiudadesIntencion) -> Unit,
    onGeolocalizacionClick: () -> Unit,
    onUbicacionClick: () -> Unit,
    isLoading: Boolean
) {
    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather App",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
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

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
            }

        }
    }
}