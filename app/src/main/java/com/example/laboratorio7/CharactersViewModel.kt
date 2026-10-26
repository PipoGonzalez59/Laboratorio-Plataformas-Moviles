
package com.example.laboratorio7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio7.database.DatabaseProvider
import com.example.laboratorio7.database.toModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false,
    val onlyFavorites: Boolean = false
)

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    // Obtenemos el DAO de personajes
    private val characterDao = DatabaseProvider
        .getDatabase(application)
        .characterDao()

    // Estado que observa la pantalla
    private val _state = MutableStateFlow(CharactersState())
    val state: StateFlow<CharactersState> = _state

    private var loadingJob: Job? = null

    init {
        getCharacters()
        observeCharacters()
    }

    // Cargamos los personajes desde Room
    fun getCharacters() {

        loadingJob?.cancel()

        _state.value = _state.value.copy(
            isLoading = true,
            hasError = false
        )

        loadingJob = viewModelScope.launch {

            try {
                delay(4000)

                val characters = characterDao.getAllCharacters().first()

                _state.value = _state.value.copy(
                    isLoading = false,
                    data = characters.map { it.toModel() },
                    hasError = false
                )

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasError = true
                )
            }
        }
    }

    // Observamos los cambios de Room
    private fun observeCharacters() {

        viewModelScope.launch {

            characterDao.getAllCharacters().collect { characters ->

                _state.value = _state.value.copy(
                    data = characters.map { it.toModel() }
                )
            }
        }
    }

    // Cambiar el favorito de un personaje
    fun changeFavorite(character: Character) {

        viewModelScope.launch {

            characterDao.updateFavorite(
                id = character.id,
                isFavorite = !character.isFavorite
            )
        }
    }

    // Activar o desactivar el filtro
    fun changeFavoriteFilter() {

        _state.value = _state.value.copy(
            onlyFavorites = !_state.value.onlyFavorites
        )
    }

    // Obtener los personajes que debemos mostrar
    fun getVisibleCharacters(): List<Character> {

        if (_state.value.onlyFavorites) {
            return _state.value.data.filter { it.isFavorite }
        }

        return _state.value.data
    }

    // Mostrar error si tocan la pantalla de carga
    fun showError() {

        if (_state.value.isLoading) {

            loadingJob?.cancel()

            _state.value = _state.value.copy(
                isLoading = false,
                hasError = true
            )
        }
    }
}
