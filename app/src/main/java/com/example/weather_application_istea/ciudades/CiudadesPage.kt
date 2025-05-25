package com.example.weather_application_istea.ciudades

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.models.Ciudad
import com.example.weather_application_istea.repository.RepositoryApi
import com.google.android.gms.location.LocationServices

@Composable
fun CiudadesPage(
    navController: NavController,
    listaDeCiudades: List<Ciudad>
) {
    val context = LocalContext.current

    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }


    val doGeolocalizacion: () -> Unit = {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permisoLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient
                .lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        navController
                            .navigate("clima/${location.latitude}/${location.longitude}")
                    } else {
                    }
                }
        }
        Unit
    }

    val viewModel: CiudadesViewModel = viewModel {
        CiudadesViewModel(
            navController   = navController,
            listaDeCiudades = listaDeCiudades,
            repository      = RepositoryApi()
        )
    }

    CiudadesView(
        estado               = viewModel.estado,
        onAction             = { intent -> viewModel.ejecutar(intent) },
        onGeolocalizacionClick = doGeolocalizacion
    )
}
