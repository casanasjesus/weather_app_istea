package com.example.weather_application_istea.ciudades

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
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
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority

@Composable
fun CiudadesPage(
    navController: NavController,
    listaDeCiudades: List<Ciudad>
) {
    val context = LocalContext.current
    val locationRequest = LocationRequest
        .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000) // cada 1 seg
        .setMaxUpdates(1) // Solo queremos una vez
        .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            if (location != null) {
                navController.navigate("clima/${location.latitude}/${location.longitude}")
            } else {
                Toast.makeText(context, "No se pudo obtener ubicación en tiempo real", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    navController.navigate("clima/${location.latitude}/${location.longitude}")
                } else {
                    // Si lastLocation es null, pedimos una ubicación actual

                }
            }
        }
    }

    var doUbicacion: () -> Unit = {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val locationRequest = LocationRequest
                .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMaxUpdates(1)
                .build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val updatedLocation = locationResult.lastLocation
                    if (updatedLocation != null) {
                        navController.navigate("clima/${updatedLocation.latitude}/${updatedLocation.longitude}")
                    } else {
                        Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }


    val viewModel: CiudadesViewModel = viewModel {
        CiudadesViewModel(
            navController   = navController,
            listaDeCiudades = listaDeCiudades,
            repository      = RepositoryApi()
        )
    }

    CiudadesView(
        estado                 = viewModel.estado,
        onAction               = { intent -> viewModel.ejecutar(intent) },
        onGeolocalizacionClick = doGeolocalizacion,
        onUbicacionClick       = doUbicacion
    )
}
