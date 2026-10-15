
package com.example.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.laboratorio7.ui.theme.Laboratorio7Theme
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object CharactersDestination

@Serializable
data class CharacterDetailsDestination(val id: Int)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Laboratorio7Theme {

                // Controlador de navegacion
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LoginDestination
                ) {

                    // Primera pantalla: Login
                    composable<LoginDestination> {

                        LoginScreen(
                            onLoginClick = {
                                navController.navigate(CharactersDestination) {
                                    popUpTo<LoginDestination> {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    // Segunda pantalla: Lista de personajes
                    composable<CharactersDestination> {

                        CharactersScreen(
                            onCharacterClick = { id ->
                                navController.navigate(
                                    CharacterDetailsDestination(id)
                                )
                            }
                        )
                    }

                    // Tercera pantalla: Detalles del personaje
                    composable<CharacterDetailsDestination> { backStackEntry ->

                        val datos = backStackEntry.toRoute<CharacterDetailsDestination>()

                        CharacterDetailsScreen(
                            id = datos.id,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
