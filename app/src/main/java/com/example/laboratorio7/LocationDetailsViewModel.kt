package com.example.laboratorio7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.laboratorio7.database.DatabaseProvider
import com.example.laboratorio7.database.toModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class LocationDetailsState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)

class LocationDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    // Obtenemos el ID desde la navegacion
    private val id = savedStateHandle.toRoute<LocationDetailsDestination>().id

    // Obtenemos el DAO de ubicaciones
    private val locationDao = DatabaseProvider
        .getDatabase(application)
        .locationDao()

    // Estado de la pantalla
    private val _state = MutableStateFlow(LocationDetailsState())
    val state: StateFlow<LocationDetailsState> = _state

    private var loadingJob: Job? = null

    init {
        getLocation()
    }

    // Obtenemos la ubicacion desde Room
    fun getLocation() {

        loadingJob?.cancel()

        _state.value = LocationDetailsState(
            isLoading = true
        )

        loadingJob = viewModelScope.launch {

            try {

                // Simulamos los 2 segundos de carga
                delay(2000)

                val location = locationDao.getLocationById(id).first()

                if (location != null) {

                    _state.value = LocationDetailsState(
                        isLoading = false,
                        data = location.toModel(),
                        hasError = false
                    )

                } else {

                    _state.value = LocationDetailsState(
                        isLoading = false,
                        hasError = true
                    )
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {

                _state.value = LocationDetailsState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    // Mostramos error si el usuario toca el loading
    fun showError() {

        if (_state.value.isLoading) {

            loadingJob?.cancel()

            _state.value = LocationDetailsState(
                isLoading = false,
                hasError = true
            )
        }
    }
}
