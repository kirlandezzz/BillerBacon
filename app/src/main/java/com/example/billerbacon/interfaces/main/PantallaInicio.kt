package com.example.billerbacon.interfaces.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billerbacon.clases.Suscripcion
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaInicio(navController: NavController?) {
    var lista = ArrayList<Suscripcion>()
    val suscripcion = Suscripcion(imagen = "Xbox", nombre = "Game Pass",
        fechaInicio = LocalDate.of(2024, 1, 1), fechaCaducidad = LocalDate.of(2024, 3, 1),
        precio = 12.99)
    lista.add(suscripcion)
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFffe8c0))) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            items(lista) {
                    item -> Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .width(400.dp)
                    .height(100.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                .border(BorderStroke(6.dp, Color.Gray), RoundedCornerShape(15.dp))
                .clickable {  }
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${item.imagen}   ")
                        Text(text = "${item.precio}   ")
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Gray)
                            .width(50.dp)
                            .wrapContentSize(Alignment.Center)
                            .height(50.dp), contentAlignment = Alignment.Center) {
                            Text(text = "${item.calcularFecha()}    ", modifier = Modifier
                                .wrapContentSize(Alignment.Center)
                                .fillMaxWidth()
                                .padding(start = 10.dp),
                                fontSize = 25.sp,
                                color = Color.White)
                        }
                    }
                }
            }
            }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview() {
    PantallaInicio(navController = null)
}