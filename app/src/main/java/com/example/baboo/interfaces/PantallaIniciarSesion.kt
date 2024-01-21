package com.example.baboo.interfaces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIniciarSesion() {
    var correo by rememberSaveable { mutableStateOf("") } //Variable para el campo correo
    var clave by rememberSaveable { mutableStateOf("") } //Variable para el campo clave
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        //Texto de iniciar sesion
        Text(text = "Iniciar sesión", fontSize = 25.sp)
        Spacer(modifier = Modifier.size(25.dp))
        //TextField correo
        OutlinedTextField(value = correo,
            onValueChange = {correo = it},
            placeholder = { Text(text = "Correo Electrónico", fontSize = 13.sp)}
        )
        Spacer(modifier = Modifier.size(25.dp))
        //TextField clave
        OutlinedTextField(value = clave,
            onValueChange = {clave = it},
            placeholder = { Text(text = "Clave")},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.size(45.dp))
        //Boton iniciar
        Button(onClick = { }, shape = RectangleShape, modifier = Modifier.size(250.dp, 50.dp)) {
            Text(text = "Iniciar")
        }
    }
}