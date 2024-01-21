package com.example.billerbacon.interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baboo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmacionClave by rememberSaveable { mutableStateOf("") }
    var errorClave by rememberSaveable { mutableStateOf(false) }
    val myFontFamily = FontFamily(Font(R.font.inter))
    val myTextStyle = TextStyle(
        fontFamily = myFontFamily
    )

    Column(
        modifier = Modifier
            .background(Color(0xFFffe8c0)),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registrarse",style = myTextStyle, fontSize = 25.sp)
        Spacer(modifier = Modifier.size(25.dp))

        // Campos de entrada
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text(text = "Nombre completo", fontSize = 13.sp) }
        )
        Spacer(modifier = Modifier.size(25.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            placeholder = { Text(text = "Correo Electrónico", fontSize = 13.sp) }
        )
        Spacer(modifier = Modifier.size(25.dp))

        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            placeholder = { Text(text = "Clave") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.size(25.dp))

        OutlinedTextField(
            value = confirmacionClave,
            onValueChange = { confirmacionClave = it },
            placeholder = { Text(text = "Confirmar Clave") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.size(25.dp))

        // Mensaje de error para claves que no coinciden
        if (errorClave) {
            Text(text = "Las claves no coinciden", color = androidx.compose.ui.graphics.Color.Red)
            Spacer(modifier = Modifier.size(15.dp))
        }

        // Botón para registrar
        Button(
            onClick = {
                errorClave = clave != confirmacionClave
                if (!errorClave) {
                    // TODO: Manejar registro
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(250.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
        ) {
            Text(text = "Registrarse",style = myTextStyle, color = Color.Black)
        }
    }
}
