
package com.example.laboratorio7

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(
    onRetryClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icono de error
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Mensaje de error
        Text(
            text = "Error al obtener la informacion.\nIntenta de nuevo.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Boton para volver a cargar
        Button(
            onClick = onRetryClick
        ) {
            Text(text = "Reintentar")
        }
    }
}
