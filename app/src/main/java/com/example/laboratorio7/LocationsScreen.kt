
package com.example.laboratorio7

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    viewModel: LocationsViewModel = viewModel()
) {

    // Observamos el estado del ViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Locations")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (state.isLoading) {

                // Mostramos la pantalla de carga
                LoadingScreen(
                    onLoadingClick = {
                        viewModel.showError()
                    }
                )

            } else if (state.hasError) {

                // Mostramos la pantalla de error
                ErrorScreen(
                    onRetryClick = {
                        viewModel.getLocations()
                    }
                )

            } else {

                // Mostramos las ubicaciones obtenidas desde Room
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    items(
                        items = state.data,
                        key = { it.id }
                    ) { location ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onLocationClick(location.id)
                                }
                                .padding(16.dp)
                        ) {

                            // Nombre de la ubicacion
                            Text(
                                text = location.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            // Tipo de ubicacion
                            Text(
                                text = location.type,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
