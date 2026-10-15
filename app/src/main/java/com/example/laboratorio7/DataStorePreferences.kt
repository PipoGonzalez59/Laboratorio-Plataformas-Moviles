package com.example.laboratorio7

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStorePreferences(private val context: Context) {

    private val nameKey = stringPreferencesKey("name")

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[nameKey] = name
        }
    }

    suspend fun getName(): String {
        val preferences = context.dataStore.data.first()
        return preferences[nameKey] ?: ""
    }

    suspend fun deleteName() {
        context.dataStore.edit { preferences ->
            preferences.remove(nameKey)
        }
    }

    val userName = context.dataStore.data.map { preferences ->
        preferences[nameKey] ?: ""
    }
}
