package com.example.laboratorio7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio7.database.DatabaseProvider
import com.example.laboratorio7.database.toEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyncViewModel(application: Application) : AndroidViewModel(application) {

    private val database = DatabaseProvider.getDatabase(application)

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    init {
        syncData()
    }

    fun syncData() {

        viewModelScope.launch {

            _isLoading.value = true
            _hasError.value = false

            try {

                // Esperamos 4 segundos
                delay(4000)

                val characterDao = database.characterDao()
                val locationDao = database.locationDao()

                // Solo insertamos si las tablas estan vacias
                if (characterDao.getCharactersCount() == 0) {

                    val characters = CharacterDb().getAllCharacters()

                    characterDao.insertCharacters(
                        characters.map { it.toEntity() }
                    )
                }

                if (locationDao.getLocationsCount() == 0) {

                    val locations = LocationDb().getAllLocations()

                    locationDao.insertLocations(
                        locations.map { it.toEntity() }
                    )
                }

                _isLoading.value = false

            } catch (e: Exception) {

                _hasError.value = true
                _isLoading.value = false
            }
        }
    }
}
