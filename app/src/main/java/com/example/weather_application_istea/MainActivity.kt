package com.example.weather_application_istea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weather_application_istea.ciudades.CiudadesPage
import com.example.weather_application_istea.clima.ClimaPage
import com.example.weather_application_istea.ui.theme.Weather_Application_ISTEATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Weather_Application_ISTEATheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "" // Si hay ciudad guardada, ir a clima, si no, ir a la lista de ciudades
                ) {
                    composable("ciudades") {
                        CiudadesPage(
                            navController
                        )
                    }
                    composable("clima") {
                        ClimaPage(
                            navController
                        )
                    }
                }
            }
        }
    }
}

