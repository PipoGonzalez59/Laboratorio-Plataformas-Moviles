package com.example.laboratorio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio4.ui.theme.Laboratorio4Theme
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.size

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Laboratorio4Theme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize()
                            .border(
                                width = 4.dp,
                                color = Color(0xFF146B2E)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logouvg),
                            contentDescription = "Logo UVG",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(330.dp)
                                .alpha(0.15f),
                            contentScale = ContentScale.Fit
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(
                                modifier = Modifier.height(130.dp)
                            )

                            Text(
                                text = "Universidad del Valle\nde Guatemala",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(
                                modifier = Modifier.height(32.dp)
                            )

                            Text(
                                text = "Programación de plataformas\nmóviles, Sección 30",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(
                                modifier = Modifier.height(28.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Text(
                                    text = "INTEGRANTES",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Jose Gonzalez\nRaquel Vega\nPablo Mendez",
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(24.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Text(
                                    text = "CATEDRÁTICO",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Juan Carlos Durini"
                                )
                            }

                            Spacer(
                                modifier = Modifier.height(28.dp)
                            )

                            Text(
                                text = "Jose Carlos Gonzalez\n25652",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}