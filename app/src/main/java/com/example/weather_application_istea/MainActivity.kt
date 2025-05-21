package com.example.weather_application_istea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                val listaDeCiudades = remember { mutableStateListOf<Ciudad>() }
                val currentCiudad = remember { mutableStateOf<Ciudad?>(null) }
                val startPage = if (currentCiudad.value != null) "clima" else "ciudades"

                NavHost(
                    navController = navController,
                    startDestination = startPage
                ) {
                    composable("ciudades") {
                        CiudadesPage(
                            navController,
                            listaDeCiudades
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

