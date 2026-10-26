package com.example.laboratorio7

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data object CharactersGraph

@Serializable
data object LocationsGraph

@Serializable
data object LocationsDestination

@Serializable
data class LocationDetailsDestination(val id: Int)

@Serializable
data object ProfileDestination

@Composable
fun MainScreen(onLogoutClick: () -> Unit) {

    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState().value
    val rutaActual = backStackEntry?.destination?.route ?: ""

    Scaffold(
        bottomBar = {

            NavigationBar {

                // Boton de Characters
                NavigationBarItem(
                    selected = rutaActual.contains("Characters"),
                    onClick = {
                        navController.navigate(CharactersGraph) {
                            popUpTo(CharactersGraph) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Characters"
                        )
                    },
                    label = {
                        Text("Characters")
                    }
                )

                // Boton de Locations
                NavigationBarItem(
                    selected = rutaActual.contains("Locations") ||
                            rutaActual.contains("LocationDetails"),
                    onClick = {
                        navController.navigate(LocationsGraph) {
                            popUpTo(CharactersGraph) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = "Locations"
                        )
                    },
                    label = {
                        Text("Locations")
                    }
                )

                // Boton de Profile
                NavigationBarItem(
                    selected = rutaActual.contains("Profile"),
                    onClick = {
                        navController.navigate(ProfileDestination) {
                            popUpTo(CharactersGraph) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CharactersGraph,
            modifier = Modifier.padding(innerPadding)
        ) {

            // Grafo de personajes
            navigation<CharactersGraph>(
                startDestination = CharactersDestination
            ) {

                // Lista de personajes
                composable<CharactersDestination> {

                    CharactersScreen(
                        onCharacterClick = { id ->
                            navController.navigate(
                                CharacterDetailsDestination(id)
                            )
                        }
                    )
                }

                // Detalles de personajes
                composable<CharacterDetailsDestination> { backStackEntry ->

                    val datos = backStackEntry.toRoute<CharacterDetailsDestination>()

                    val detailsViewModel: CharacterDetailsViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry
                    )

                    CharacterDetailsScreen(
                        id = datos.id,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        viewModel = detailsViewModel
                    )
                }
            }

            // Grafo de ubicaciones
            navigation<LocationsGraph>(
                startDestination = LocationsDestination
            ) {

                // Lista de ubicaciones
                composable<LocationsDestination> {

                    LocationsScreen(
                        onLocationClick = { id ->
                            navController.navigate(
                                LocationDetailsDestination(id)
                            )
                        }
                    )
                }

                // Detalles de ubicaciones
                composable<LocationDetailsDestination> { backStackEntry ->

                    val datos = backStackEntry.toRoute<LocationDetailsDestination>()

                    LocationDetailsScreen(
                        id = datos.id,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }

            // Pantalla de perfil
            composable<ProfileDestination> {

                ProfileScreen(
                    onLogoutClick = onLogoutClick
                )
            }
        }
    }
}
