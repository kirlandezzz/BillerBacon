package com.example.billerbacon.interfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.R
import com.example.billerbacon.navegacion.Navegacion
import com.example.billerbacon.viewmodels.ViewModelLogin
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaIniciarSesion(navController: NavController) {
    var correo by rememberSaveable { mutableStateOf("") } //Variable para el campo correo
    var clave by rememberSaveable { mutableStateOf("") } //Variable para el campo clave
    val myFontFamily = FontFamily(Font(R.font.inter))

    val myTextStyle = TextStyle(
        fontFamily = myFontFamily
    )
    val viewModelLogin: ViewModelLogin = viewModel()
    val mensajeError by viewModelLogin.mensajeError.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    if (mensajeError != null) {
        LaunchedEffect(key1 = mensajeError) {
            snackbarHostState.showSnackbar(
                message = mensajeError!!,
                duration = SnackbarDuration.Short
            )
        }
    }
    Column(
        modifier = Modifier
            .background(Color(0xFFffe8c0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Texto de iniciar sesion
        Text(text = "Iniciar sesión", style = myTextStyle, fontSize = 25.sp)
        Spacer(modifier = Modifier.size(25.dp))
        //TextField correo
        OutlinedTextField(value = correo,
            onValueChange = { correo = it },
            placeholder = {
                Text(
                    text = "Correo Electrónico",
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.size(25.dp))
        //TextField clave
        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            placeholder = { Text(text = "Clave") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.size(45.dp))
        //Boton iniciar
        Button(
            onClick = {
                viewModelLogin.iniciarSesion(
                    correo,
                    clave,
                    home = {
                        navController.navigate(Navegacion.PantallaInicio.ruta)
                    }
                )
                println("entra")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(250.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
        ) {
            Text(text = "Iniciar", style = myTextStyle, color = Color.Black)
        }
    }
    SnackbarHost(hostState = snackbarHostState)
}