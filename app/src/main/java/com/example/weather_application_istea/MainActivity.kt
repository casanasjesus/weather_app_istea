package com.example.weather_application_istea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather_application_istea.ciudades.CiudadesPage
import com.example.weather_application_istea.clima.ClimaPage
import com.example.weather_application_istea.storage.CiudadStorage
import com.example.weather_application_istea.ui.theme.Weather_Application_ISTEATheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val ciudadStorage = CiudadStorage.obtenerCiudadStorage(this@MainActivity)

            setContent {
                Weather_Application_ISTEATheme {
                    val navController = rememberNavController()

                    val startDestination =
                        if (ciudadStorage != null)
                            "clima/${ciudadStorage.lat}/${ciudadStorage.lon}"
                        else
                            "ciudades"

                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable("ciudades") {
                            CiudadesPage(navController = navController)
                        }
                        composable("clima/{lat}/{lon}") { backStackEntry ->
                            val lat = backStackEntry.arguments?.getString("lat")?.toFloatOrNull()
                            val lon = backStackEntry.arguments?.getString("lon")?.toFloatOrNull()
                            ClimaPage(
                                navController,
                                lat = lat,
                                lon = lon
                            )
                        }
                    }
                }
            }
        }
    }
}
