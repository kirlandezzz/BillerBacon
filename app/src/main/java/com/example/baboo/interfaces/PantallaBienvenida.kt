package com.example.baboo.interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baboo.navegacion.Navegacion

@Composable
fun PantallaBienvenida(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(listOf(Color(0xFF86509e), Color(0xFF95d0f2))))) {
        //Box para colocar el logo
        Box(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

        }
        //Mensaje de bienvenida
        Box(Modifier.padding(bottom = 50.dp)) {
            Column(Modifier.fillMaxWidth()) {
                Text(text = "Bienvenido a Baboo", fontSize = 35.sp, textAlign = TextAlign.Center)
                Text(text = "Baboo es una aplicacion de... zighrufllllllllllLHJKSGDJKSfJKLHSdfgoSIydfgosdefghyaosdufghowsefgowfuygowfuygwofyugwofuygoswyfgoswuyfgo")
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
        Button(onClick = accion, shape = RectangleShape, modifier = Modifier.size(250.dp, 50.dp)) {
            Text(text = texto)
        }
    }
}