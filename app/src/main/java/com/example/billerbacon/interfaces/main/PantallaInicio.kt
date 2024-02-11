package com.example.billerbacon.interfaces.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.clases.Suscripcion
import com.example.billerbacon.navegacion.Navegacion
import com.example.billerbacon.viewmodels.ViewModelMain
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavController? = null) {
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    var showDialog by remember { mutableStateOf(false) }
    val viewModel:ViewModelMain = viewModel()
    val suscripciones by viewModel.suscripciones.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val fecha = LocalDate.parse("10-02-2024", formatter)
    val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
    var paddingNumeros: Dp
    println("usuarioID: $usuarioID")

    LaunchedEffect(usuarioID) {
        usuarioID?.let { id ->
            viewModel.cargarSuscripcionesDeUsuario(id)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                Text(text = currentDate, color = Color.Black)
            }
        }, navigationIcon = {
            IconButton(onClick = { /* handle navigation icon click */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.Black)
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFFffe8c0)
        )
        )
    },floatingActionButton = {
        FloatingActionButton(
            onClick = { showDialog = true },
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }, floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFffe8c0))
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                items(suscripciones) { item ->
                    paddingNumeros = if (item.calcularFecha() > 9) 10.dp else 18.dp
                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .width(400.dp)
                        .height(100.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .border(BorderStroke(6.dp, Color.LightGray), RoundedCornerShape(15.dp))
                        .clickable { navController?.navigate("PantallaInformacion") }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(Modifier.width(70.dp)) {
                                Text(text = item.imagen)
                            }
                            Box(Modifier.width(50.dp)) {
                                Text(text = "${item.precio}")
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.LightGray)
                                    .width(50.dp)
                                    .wrapContentSize(Alignment.Center)
                                    .height(50.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${item.calcularFecha()}    ",
                                    modifier = Modifier
                                        .wrapContentSize(Alignment.Center)
                                        .fillMaxWidth()
                                        .padding(start = paddingNumeros),
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            blurRadius = 6f,
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFffe8c0)), contentAlignment = Alignment.Center
            ) {

                if (showDialog) {
                    var nombreSuscripcion by remember { mutableStateOf("") }
                    var precioSuscripcion by remember { mutableStateOf("") }
                    var fechaInicio by remember { mutableStateOf("") }
                    var fechaCaducidad by remember { mutableStateOf("") }

                    AlertDialog(onDismissRequest = { showDialog = false }, title = {
                        Text(
                            text = "Nueva Suscripción",
                        )
                    }, text = {
                        Column {
                            OutlinedTextField(
                                value = nombreSuscripcion,
                                onValueChange = { nombreSuscripcion = it },
                                label = { Text("Nombre de la suscripción") },

                                )
                            OutlinedTextField(
                                value = precioSuscripcion,
                                onValueChange = { precioSuscripcion = it },
                                label = { Text("Precio") },

                                )
                            OutlinedTextField(
                                value = fechaInicio,
                                onValueChange = { fechaInicio = it },
                                label = { Text("Fecha inicio") },

                                )
                            OutlinedTextField(
                                value = fechaCaducidad,
                                onValueChange = { fechaCaducidad = it },
                                label = { Text("Fecha caducidad") },

                                )
                        }
                    }, confirmButton = {
                        Button(
                            onClick = {
                                try {
                                    // Convertir fechas de String a Timestamp
                                    val fechaInicioParsed = LocalDate.parse(fechaInicio, formatter)
                                        .atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    val fechaCaducidadParsed =
                                        LocalDate.parse(fechaCaducidad, formatter)
                                            .atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    val timestampInicio = Timestamp(Date.from(fechaInicioParsed))
                                    val timestampCaducidad =
                                        Timestamp(Date.from(fechaCaducidadParsed))

                                    // Conversión de precio de String a Double
                                    val precioParsed = precioSuscripcion.toDoubleOrNull()
                                    if (precioParsed == null) {
                                        // Manejar error de conversión
                                        return@Button
                                    }

                                    // Crear objeto Suscripcion con Timestamps
                                    val suscripcion = Suscripcion(
                                        imagen = nombreSuscripcion,
                                        nombre = nombreSuscripcion,
                                        fechaInicio = timestampInicio,
                                        fechaCaducidad = timestampCaducidad,
                                        precio = precioParsed,
                                        usuarioID = usuarioID!!
                                    )

                                    // Llamar a ViewModel para agregar suscripción
                                    viewModel.agregarSuscripcion(suscripcion)

                                    // Resetear diálogo
                                    showDialog = false
                                } catch (e: DateTimeParseException) {
                                    // Manejar errores de formato de fecha
                                }
                            },
                            shape = RoundedCornerShape(50)
                        ) {
                            Text("Subir")
                        }
                    },


                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = false }, shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    "Cancelar", color = MaterialTheme.colorScheme.error
                                )
                            }
                        }, containerColor = Color(0xFFffe8c0), shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun prev() { PantallaInicio(null)
}
