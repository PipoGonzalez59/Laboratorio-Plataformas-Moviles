package com.example.laboratorio5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laboratorio5.ui.theme.Laboratorio5Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Laboratorio5Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    PantallaPrincipal(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Laboratorio 5",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Frontend Básico",
            style = MaterialTheme.typography.titleLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Mi cumpleaños",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "26 de marzo de 2026"
                )

                Text(
                    text = "Día: jueves"
                )
            }
        }

        Button(
            onClick = {

                Toast.makeText(
                    context,
                    "Jose Carlos Gonzalez Argueta",
                    Toast.LENGTH_SHORT
                ).show()

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Iniciar")
        }


        Button(
            onClick = {

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.spotify.music"
                    )
                )

                context.startActivity(intent)

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Descargar Spotify")
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Buta",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    IconButton(
                        onClick = {

                            val direccion =
                                "Buta, 16 Calle 7-17, Ciudad de Guatemala 01010"

                            val uri = Uri.parse(
                                "geo:0,0?q=${Uri.encode(direccion)}"
                            )

                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                uri
                            )

                            context.startActivity(intent)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Abrir ubicación"
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "16 Calle 7-17, Ciudad de Guatemala 01010"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Horario:"
                )

                Text(
                    text = "Lunes a domingo de 12:00 AM a 10:00 PM"
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                Button(
                    onClick = {

                        Toast.makeText(
                            context,
                            "Comida japonesa, Caro: Q120 - Q200 por persona",
                            Toast.LENGTH_LONG
                        ).show()

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Detalles")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {

    Laboratorio5Theme {
        PantallaPrincipal()
    }
}