package com.example.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio7.ui.theme.Laboratorio7Theme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object MainDestination

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailsDestination(val id: Int)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Laboratorio7Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStorePreferences = remember(context) {
        DataStorePreferences(context)
    }

    // Revisamos si hay una sesion guardada
    val nombreGuardado by produceState<String?>(initialValue = null) {
        value = dataStorePreferences.getName()
    }

    // Mostramos una carga mientras revisamos DataStore
    if (nombreGuardado == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {

        val navController = rememberNavController()

        // Elegimos la pantalla inicial
        val pantallaInicial = if (nombreGuardado!!.isNotBlank()) {
            MainDestination
        } else {
            LoginDestination
        }

        NavHost(
            navController = navController,
            startDestination = pantallaInicial
        ) {

            // Pantalla Login
            composable<LoginDestination> {

                LoginScreen(
                    onLoginClick = { nombre ->

                        scope.launch {

                            // Guardamos el nombre
                            dataStorePreferences.saveName(nombre)

                            // Navegamos a Main y eliminamos Login
                            navController.navigate(MainDestination) {
                                popUpTo<LoginDestination> {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }

            // Pantalla principal
            composable<MainDestination> {

                MainScreen(
                    onLogoutClick = {

                        scope.launch {

                            // Eliminamos el nombre guardado
                            dataStorePreferences.deleteName()

                            // Regresamos a Login y limpiamos el historial
                            navController.navigate(LoginDestination) {
                                popUpTo<MainDestination> {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
