
package com.example.laboratorio7

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(onLoadingClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onLoadingClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Circulo de carga
        CircularProgressIndicator()

        Spacer(modifier = Modifier.height(15.dp))

        // Texto de carga
        Text(text = "Cargando")
    }
}
