package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ────────────────────────────────
        // Campo de texto OUTLINED por defecto
        OutlinedTextField(
            value        = query,
            onValueChange= onQueryChange,
            modifier     = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder  = { Text("Buscar ciudad") },
            leadingIcon  = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine   = true,
            shape        = RoundedCornerShape(12.dp)
        )
        // ────────────────────────────────

        Spacer(modifier = Modifier.width(12.dp))

        // Botón circular
        IconButton(
            onClick  = onGeolocalizacionClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        ) {
            Icon(
                Icons.Default.MyLocation,
                contentDescription = "Mi ubicación",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
