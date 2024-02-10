package com.example.billerbacon.interfaces.main

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaInformacion() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFffe8c0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(25.dp))
            .width(350.dp)
            .height(500.dp)
            .padding(10.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box() {
                        Text(text = "Netflix")
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        GenerarTexto(texto = "Precio Actual")
                        GenerarTexto(texto = "12.99€")
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
                Box(Modifier.fillMaxWidth()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.background(Color.Gray)
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Próxima factura")
                            Text(text = "12.99€")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenerarTexto(texto: String) {
    Text(text = texto, fontSize = 20.sp)
}

@Preview
@Composable
fun Preview() {
    PantallaInformacion()
}