package com.example.weather_application_istea.clima

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weather_application_istea.models.ClimaActual


import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimaView(
    navController: NavController,
    estado: ClimaEstado
) {
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
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
                .padding(padding)
        ) {
            when (estado) {
                is ClimaEstado.Cargando -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                is ClimaEstado.Error -> {
                    Text(
                        "Ocurrió un error: ${estado.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ClimaEstado.Vacio -> {
                    Text(
                        "No hay datos para mostrar.",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is ClimaEstado.Resultado -> {
                    ClimaContent(
                        navController = navController,
                        clima = estado.clima,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ClimaContent(
    navController: NavController,
    clima: ClimaActual,
    modifier: Modifier = Modifier
) {
    val weather = clima.weather.firstOrNull()
    val iconUrl = weather?.icon?.let { "https://openweathermap.org/img/wn/${it}@4x.png" }
    val context = LocalContext.current

    // Sample forecast data
    val forecastData = listOf(
        Triple("Now", clima.main.temp_min.toFloat(), clima.main.temp_max.toFloat()),
        Triple("12h", 18f, 25f),
        Triple("24h", 16f, 27f),
        Triple("36h", 17f, 26f),
        Triple("48h", 19f, 28f)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primera tarjeta con la información principal del clima
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Weather Icon
                if (iconUrl != null) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = iconUrl,
                            contentDescription = weather.description,
                            modifier = Modifier.size(48.dp),
                            contentScale = ContentScale.Fit,
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Location Name
                Text(
                    text = clima.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                // Weather Description
                Text(
                    text = weather?.description
                        ?.replaceFirstChar { it.uppercase() }
                        ?: "Sin descripción",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(16.dp))

                // Current Temperature
                Text(
                    text = "${clima.main.temp.toInt()}°",
                    fontSize = 64.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(8.dp))

                // Min/Max Temperatures
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Máx: ${clima.main.temp_max.toInt()}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        "Mín: ${clima.main.temp_min.toInt()}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(16.dp))

                // Additional Metrics
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AuxMetric("💧", "Humedad", "${clima.main.humidity}%")
                    AuxMetric("🔼", "Presión", "${clima.main.pressure} hPa")
                    AuxMetric("💨", "Viento", "${clima.wind.speed} m/s")
                }

                Spacer(Modifier.height(16.dp))

                // Action Buttons (dentro de la primera tarjeta)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    val ciudad = clima.name
                    val temperatura = clima.main.temp.toInt()
                    val descripcion = weather?.description?.replaceFirstChar { it.uppercase() } ?: "Sin descripción"

                    Button(
                        onClick = {
                            navController.navigate("ciudades") {
                                popUpTo("ciudades") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Cambiar de ciudad",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Cambiar de ciudad")
                    }

                    Button(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "El clima en $ciudad es de $temperatura° con $descripcion. 🌤️"
                                )
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Compartir clima con...")
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Compartir clima",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Compartir clima")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Segunda tarjeta con el gráfico
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pronóstico de temperaturas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                GraficoMaxMin(forecastData)
            }
        }
    }
}

@Composable
private fun AuxMetric(icon: String, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 24.sp)
        Text(label, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun GraficoMaxMin(extremos: List<Triple<String, Float, Float>>) {
    // Preparar datos: [ [min1, max1], [min2, max2], ... ]
    val entries = extremos.mapIndexed { index, (_, min, max) ->
        listOf(
            FloatEntry(index.toFloat(), min),  // Mínima
            FloatEntry(index.toFloat(), max)  // Máxima
        )
    }.flatten()  // Aplanar a lista simple: [min1, max1, min2, max2, ...]

    // Modelo para el gráfico
    val chartModel = entryModelOf(entries)

    // Gráfico
    Chart(
        chart = lineChart(
            lines = listOf(
                lineSpec(  // Línea azul para mínimas
                    lineColor = Color.Blue,
                    lineThickness = 4.dp
                ),
                lineSpec(  // Línea roja para máximas
                    lineColor = Color.Red,
                    lineThickness = 4.dp
                )
            )
        ),
        model = chartModel,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                extremos.getOrNull(value.toInt())?.first ?: ""
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
    )
}