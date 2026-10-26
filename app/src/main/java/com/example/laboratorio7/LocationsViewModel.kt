
package com.example.laboratorio7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio7.database.DatabaseProvider
import com.example.laboratorio7.database.LocationEntity
import com.example.laboratorio7.database.toModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LocationsState(
    val isLoading: Boolean = true,
    val data: List<Location> = emptyList(),
    val hasError: Boolean = false
)

class LocationsViewModel(application: Application) : AndroidViewModel(application) {

    // Obtenemos el DAO de ubicaciones
    private val locationDao = DatabaseProvider
        .getDatabase(application)
        .locationDao()

    // Estado de la pantalla
    private val _state = MutableStateFlow(LocationsState())
    val state: StateFlow<LocationsState> = _state

    private var loadingJob: Job? = null

    init {
        getLocations()
    }

    // Obtenemos las ubicaciones desde Room
    fun getLocations() {

        loadingJob?.cancel()

        _state.value = LocationsState(
            isLoading = true
        )

        loadingJob = viewModelScope.launch {

            try {

                // Simulamos 4 segundos de carga
                delay(4000)

                val locations = locationDao.getAllLocations().first()

                _state.value = LocationsState(
                    isLoading = false,
                    data = locations.map { it.toModel() },
                    hasError = false
                )

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {

                _state.value = LocationsState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    // Mostramos error si tocan el loading
    fun showError() {

        if (_state.value.isLoading) {

            loadingJob?.cancel()

            _state.value = LocationsState(
                isLoading = false,
                hasError = true
            )
        }
    }
}
