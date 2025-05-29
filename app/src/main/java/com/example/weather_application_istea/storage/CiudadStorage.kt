package com.example.weather_application_istea.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

data class CiudadGuardada(val lat: Float, val lon: Float)

val Context.dataStore by preferencesDataStore(name = "ciudad_prefs")

object CiudadStorage {
    private val LAT_KEY = floatPreferencesKey("lat")
    private val LON_KEY = floatPreferencesKey("lon")

    suspend fun guardarCiudadStorage(context: Context, lat: Float, lon: Float) {
        context.dataStore.edit { prefs ->
            prefs[LAT_KEY] = lat
            prefs[LON_KEY] = lon
        }
    }

    suspend fun obtenerCiudadStorage(context: Context): CiudadGuardada? {
        val prefs = context.dataStore.data.first()
        val lat = prefs[LAT_KEY]
        val lon = prefs[LON_KEY]

        return if (lat != null && lon != null) CiudadGuardada(lat, lon) else null
    }
}