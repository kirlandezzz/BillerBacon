package com.example.billerbacon.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billerbacon.R

import com.example.billerbacon.navegacion.Navegacion

@Composable
fun PantallaBienvenida(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFffe8c0))
    ) {
        Text(
            text = "Bienvenido a BillerBacon",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        //Box para colocar el logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.billerbacon),
                contentDescription = "Icono de la aplicacion"
            )
        }
        //Mensaje de bienvenida
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "BillerBacon es una aplicación que se encarga de alertar sobre la caducidad de tus " +
                            "suscripciones a gimnasios, revistas, plataformas de streaming y entre otras activas.\n",
                    color = Color.Black, fontSize = 15.sp,
                    modifier = Modifier.fillMaxWidth(), // Asegurarse de que el texto tenga el ancho completo del botón
                    textAlign = TextAlign.Center // Alineación del texto

                    )
            }
        }
        //Boton iniciar sesion
        CrearBoton(texto = "Iniciar sesión") {
            navController.navigate(Navegacion.PantallaIniciarSesion.ruta)
        }
        //Boton Registrarse
        CrearBoton(texto = "Registrarse") {
            navController.navigate(Navegacion.PantallaRegistro.ruta)
        }
    }
}

//Funcion para crear el componente boton
@Composable
fun CrearBoton(texto: String, accion: () -> Unit) {
    Box(Modifier.padding(vertical = 35.dp)) {
        Button(
            onClick = accion, shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(250.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(Color.White)

        ) {
            Text(text = texto, color = Color.Black)
        }
    }
}