package com.example.weather_application_istea.ciudades

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weather_application_istea.models.Ciudad

@Composable
fun ResultadoCiudadesView(
    modifier: Modifier = Modifier,
    listaCiudades: List<Ciudad>,
    onSelect: (Ciudad) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(15.dp)
    ) {
        items(listaCiudades) { ciudad ->
            Card(onClick = { onSelect(ciudad) }) {
                Text(ciudad.name)
            }
        }
    }
}