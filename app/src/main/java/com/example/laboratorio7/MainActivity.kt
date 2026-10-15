
package com.example.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio7.ui.theme.Laboratorio7Theme
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

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LoginDestination
                ) {

                    // Pantalla Login
                    composable<LoginDestination> {

                        LoginScreen(
                            onLoginClick = {
                                navController.navigate(MainDestination) {
                                    popUpTo<LoginDestination> {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    // Pantalla principal
                    composable<MainDestination> {

                        MainScreen(
                            onLogoutClick = {
                                navController.navigate(LoginDestination) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
