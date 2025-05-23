package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BuscadorYGeoView(
    query: String,
    onQueryChange: (String) -> Unit,
    onGeolocalizacionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = query,
            onValueChange = onQueryChange,
            label = { Text("Buscar ciudad") },
            singleLine = true
        )

        IconButton(
            onClick = onGeolocalizacionClick,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Usar mi ubicación"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BuscadorYGeoPreview() {}
