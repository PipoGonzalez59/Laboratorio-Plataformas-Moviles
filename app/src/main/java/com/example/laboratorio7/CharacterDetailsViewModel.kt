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
import kotlinx.coroutines.launch

data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val data: Character? = null,
    val hasError: Boolean = false
)

class CharacterDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    // Recuperamos el ID desde la navegacion
    private val id = savedStateHandle.toRoute<CharacterDetailsDestination>().id

    // Obtenemos el DAO de Room
    private val characterDao = DatabaseProvider
        .getDatabase(application)
        .characterDao()

    // Estado de la pantalla
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> = _state

    private var loadingJob: Job? = null

    init {
        getCharacter()
    }

    // Obtenemos el personaje desde Room
    fun getCharacter() {

        loadingJob?.cancel()

        _state.value = CharacterDetailsState(
            isLoading = true
        )

        loadingJob = viewModelScope.launch {

            try {

                // Esperamos 2 segundos
                delay(2000)

                // Observamos los cambios del personaje en Room
                characterDao.getCharacterById(id).collect { character ->

                    if (character != null) {

                        _state.value = CharacterDetailsState(
                            isLoading = false,
                            data = character.toModel(),
                            hasError = false
                        )

                    } else {

                        _state.value = CharacterDetailsState(
                            isLoading = false,
                            hasError = true
                        )
                    }
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {

                _state.value = CharacterDetailsState(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    // Marcamos o desmarcamos el favorito
    fun changeFavorite() {

        val character = _state.value.data ?: return

        viewModelScope.launch {

            characterDao.updateFavorite(
                id = character.id,
                isFavorite = !character.isFavorite
            )
        }
    }

    // Mostramos error si tocan la pantalla de carga
    fun showError() {

        if (_state.value.isLoading) {

            loadingJob?.cancel()

            _state.value = CharacterDetailsState(
                isLoading = false,
                hasError = true
            )
        }
    }
}
