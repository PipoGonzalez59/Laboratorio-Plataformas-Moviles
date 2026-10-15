
package com.example.laboratorio7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
data class CharactersState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false
)

class CharactersViewModel : ViewModel() {

    // Aqui guardamos el estado
    private val _state = MutableStateFlow(CharactersState())

    // Este es el estado que observara la pantalla
    val state: StateFlow<CharactersState> = _state

    // Nos sirve para controlar la carga
    private var loadingJob: Job? = null

    init {
        getCharacters()
    }

    // Funcion para obtener los personajes
    fun getCharacters() {

        loadingJob?.cancel()

        _state.value = CharactersState(
            isLoading = true,
            data = emptyList(),
            hasError = false
        )

        loadingJob = viewModelScope.launch {

            // Simulamos una espera de 4 segundos
            delay(4000)

            val characterDb = CharacterDb()
            val characters = characterDb.getAllCharacters()

            _state.value = CharactersState(
                isLoading = false,
                data = characters,
                hasError = false
            )
        }
    }

    // Funcion que se ejecuta si presionamos el loading
    fun showError() {

        if (_state.value.isLoading) {

            // Cancelamos la carga para que no aparezcan los datos
            loadingJob?.cancel()

            _state.value = CharactersState(
                isLoading = false,
                data = emptyList(),
                hasError = true
            )
        }
    }
}
