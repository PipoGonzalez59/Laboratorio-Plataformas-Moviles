package com.example.laboratorio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio6.ui.theme.Laboratorio6Theme

data class Movimiento(
    val valor: Int,
    val incremento: Boolean
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Laboratorio6Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    PantallaContador(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaContador(modifier: Modifier = Modifier) {

    var contador by remember { mutableIntStateOf(0) }
    var incrementos by remember { mutableIntStateOf(0) }
    var decrementos by remember { mutableIntStateOf(0) }
    var maximo by remember { mutableIntStateOf(0) }
    var minimo by remember { mutableIntStateOf(0) }

    val historial = remember {
        mutableStateListOf<Movimiento>()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // Nombre

        Text(
            text = "Jose Carlos Gonzalez Argueta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // Contador

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    contador--
                    decrementos++

                    if (contador < minimo) {
                        minimo = contador
                    }

                    historial.add(
                        Movimiento(
                            valor = contador,
                            incremento = false
                        )
                    )
                }
            ) {
                Text("-")
            }

            Text(
                text = contador.toString(),
                fontSize = 50.sp,
                modifier = Modifier.padding(horizontal = 25.dp)
            )

            Button(
                onClick = {
                    contador++
                    incrementos++

                    if (contador > maximo) {
                        maximo = contador
                    }

                    historial.add(
                        Movimiento(
                            valor = contador,
                            incremento = true
                        )
                    )
                }
            ) {
                Text("+")
            }
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        // Estadisticas

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Estadísticas",
                    style = MaterialTheme.typography.titleLarge
                )

                FilaEstadistica(
                    titulo = "Total incrementos:",
                    valor = incrementos
                )

                FilaEstadistica(
                    titulo = "Total decrementos:",
                    valor = decrementos
                )

                FilaEstadistica(
                    titulo = "Valor máximo:",
                    valor = maximo
                )

                FilaEstadistica(
                    titulo = "Valor mínimo:",
                    valor = minimo
                )

                FilaEstadistica(
                    titulo = "Total cambios:",
                    valor = incrementos + decrementos
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // Historial

        Text(
            text = "Historial:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(historial) { movimiento ->

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(
                            color = if (movimiento.incremento) {
                                Color(0xFF2E7D32)
                            } else {
                                Color(0xFFC62828)
                            },
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = movimiento.valor.toString(),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        // Reiniciar

        Button(
            onClick = {
                contador = 0
                incrementos = 0
                decrementos = 0
                maximo = 0
                minimo = 0
                historial.clear()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar")
        }
    }
}

@Composable
fun FilaEstadistica(
    titulo: String,
    valor: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = titulo,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = valor.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaContadorPreview() {
    Laboratorio6Theme {
        PantallaContador()
    }
}