package com.example.weather_application_istea.ciudades

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weather_application_istea.repository.RepositoryApi
import com.example.weather_application_istea.storage.CiudadStorage
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch

@Composable
fun CiudadesPage(
    navController: NavController
) {
    var isLoading by remember { mutableStateOf(false) }
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

    val storageContext = LocalContext.current
    val storageScope = rememberCoroutineScope()

    var doUbicacion: () -> Unit = {
        isLoading = true // 👈 Mostrar spinner

        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permisoLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            isLoading = false // 👈 Ocultar si no hay permiso
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val locationRequest = LocationRequest
                    .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setMaxUpdates(1)
                    .build()

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        isLoading = false // 👈 Ocultar spinner

                        val updatedLocation = locationResult.lastLocation
                        if (updatedLocation != null) {
                            val latitude: Double = updatedLocation.latitude
                            val longitude: Double = updatedLocation.longitude

                            navController.navigate("clima/${latitude}/${longitude}")

                            storageScope.launch {
                                CiudadStorage.guardarCiudadStorage(storageContext, latitude.toFloat(), longitude.toFloat())
                            }
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
            }.addOnFailureListener {
                isLoading = false // 👈 Ocultar en error
                Toast.makeText(context, "Error al obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val viewModel: CiudadesViewModel = viewModel {
        CiudadesViewModel(
            navController   = navController,
            repository      = RepositoryApi()
        )
    }

    CiudadesView(
        estado                 = viewModel.estado,
        onAction               = { intent -> viewModel.ejecutar(intent) },
        onGeolocalizacionClick = doGeolocalizacion,
        onUbicacionClick       = doUbicacion,
        isLoading              = isLoading
    )
}
