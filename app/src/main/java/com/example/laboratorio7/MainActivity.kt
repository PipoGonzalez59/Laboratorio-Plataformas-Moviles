package com.example.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio7.database.DatabaseProvider
import com.example.laboratorio7.ui.theme.Laboratorio7Theme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object SyncDestination

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

    val database = remember(context) {
        DatabaseProvider.getDatabase(context)
    }

    // Revisamos si existe una sesion guardada
    val nombreGuardado by produceState<String?>(initialValue = null) {
        value = dataStorePreferences.getName()
    }

    if (nombreGuardado == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {

        val navController = rememberNavController()

        // Si hay sesion, revisamos Room antes de entrar a Main
        val pantallaInicial = if (nombreGuardado!!.isNotBlank()) {
            SyncDestination
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
                            dataStorePreferences.saveName(nombre)

                            navController.navigate(SyncDestination) {
                                popUpTo<LoginDestination> {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }

            // Pantalla de sincronizacion
            composable<SyncDestination> {

                val syncViewModel: SyncViewModel = viewModel()
                val isLoading by syncViewModel.isLoading.collectAsStateWithLifecycle()
                val hasError by syncViewModel.hasError.collectAsStateWithLifecycle()

                if (isLoading) {

                    SyncScreen()

                } else if (hasError) {

                    // Si ocurre un error, permitimos reintentar
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                syncViewModel.syncData()
                            }
                        ) {
                            Text("Error al sincronizar. Reintentar")
                        }
                    }

                } else {

                    // Cuando termina la sincronizacion entramos a Main
                    androidx.compose.runtime.LaunchedEffect(Unit) {
                        navController.navigate(MainDestination) {
                            popUpTo<SyncDestination> {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            // Pantalla principal
            composable<MainDestination> {

                MainScreen(
                    onLogoutClick = {

                        scope.launch {

                            // Eliminamos los datos de Room
                            database.characterDao().deleteAllCharacters()
                            database.locationDao().deleteAllLocations()

                            // Eliminamos el nombre guardado
                            dataStorePreferences.deleteName()

                            // Regresamos a Login
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

