package com.example.billerbacon.interfaces.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.billerbacon.R
import com.example.billerbacon.navegacion.Navegacion
import kotlinx.coroutines.delay

@Composable
fun PantallaBienvenida(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(4000L)
        navController.navigate(Navegacion.PantallaIniciarSesion.ruta) {
            popUpTo(Navegacion.PantallaBienvenida.ruta) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFffe8c0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Bienvenido a BillerBacon",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.billerbacon),
                contentDescription = "Icono de la aplicación",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )


            Text(
                text = "Gestiona tus suscripciones de forma sencilla y nunca olvides una fecha importante.",
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
