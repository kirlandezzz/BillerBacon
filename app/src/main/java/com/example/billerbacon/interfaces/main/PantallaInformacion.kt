package com.example.billerbacon.interfaces.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.viewmodels.ViewModelMain
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.billerbacon.clases.Suscripcion
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PantallaInformacion(navController: NavController) {
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    val viewModel: ViewModelMain = viewModel()
    val suscripciones by viewModel.suscripciones.collectAsState()
    val pagerState = rememberPagerState(pageCount = { suscripciones.size })
    val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val context = LocalContext.current
    LaunchedEffect(usuarioID) {
        usuarioID?.let { id ->
            viewModel.cargarSuscripcionesDeUsuario(id)
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .background(Color(0xFFffe8c0))
                    .fillMaxHeight()
                    .width(150.dp)
            ) {

                DrawerItem(label = "Inicio", onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate("PantallaInicio")

                })
                DrawerItem(label = "Créditos", onClick = {
                    scope.launch {
                        drawerState.close()
                    }

                })
            }

        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(text = currentDate, color = Color.Black)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch {
                            drawerState.open()
                        } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFFffe8c0)
                    )
                )
            }
        ) { paddingValues ->

            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fill,
                modifier = Modifier.fillMaxWidth(),

                ) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFffe8c0))
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(25.dp))
                            .width(350.dp)
                            .height(500.dp)
                            .padding(10.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box() {
                                    Text(text = suscripciones[index].nombre)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "Precio Actual", fontSize = 20.sp)
                                    Text(text = "${suscripciones[index].precio}", fontSize = 20.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(50.dp))
                            Box(
                                Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(
                                            Color.LightGray,
                                            shape = RoundedCornerShape(25.dp)
                                        )
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                ) {
                                    GenerarEstructuraFacturacion(
                                        texto1 = "Próxima Factura",
                                        texto2 = "12.99€"
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    GenerarEstructuraFacturacion(
                                        texto1 = "Fecha",
                                        texto2 = "23-11-2024"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFFFE587),
                                            shape = RoundedCornerShape(25.dp)
                                        )
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(20.dp)
                                ) {
                                    for (i in 1..6) {
                                        GenerarEstructuraFacturacion(
                                            texto1 = "23-12-2024",
                                            texto2 = "12.99€"
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly

                    ) {
                        Button(onClick = {

                            showDialog = true
                        }) {
                            Text("Modificar")
                        }
                        if (showDialog) {
                            var nombreSuscripcion by remember { mutableStateOf("") }
                            var precioSuscripcion by remember { mutableStateOf("") }
                            var fechaInicio by remember { mutableStateOf("") }
                            var fechaCaducidad by remember { mutableStateOf("") }

                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = {
                                    Text(
                                        text = "Modificar Suscripción",
                                    )
                                },
                                text = {
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
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            try {

                                                val fechaInicioParsed =
                                                    LocalDate.parse(fechaInicio, formatter)
                                                        .atStartOfDay(ZoneId.systemDefault())
                                                        .toInstant()
                                                val fechaCaducidadParsed =
                                                    LocalDate.parse(fechaCaducidad, formatter)
                                                        .atStartOfDay(ZoneId.systemDefault())
                                                        .toInstant()
                                                val timestampInicio =
                                                    Timestamp(Date.from(fechaInicioParsed))
                                                val timestampCaducidad =
                                                    Timestamp(Date.from(fechaCaducidadParsed))
                                                val precioParsed = precioSuscripcion.toDouble()
                                                val suscripcion = Suscripcion(
                                                    imagen = nombreSuscripcion,
                                                    nombre = nombreSuscripcion,
                                                    fechaInicio = timestampInicio,
                                                    fechaCaducidad = timestampCaducidad,
                                                    precio = precioParsed,
                                                    usuarioID = usuarioID!!
                                                )
                                                viewModel.actualizarSuscripcion(
                                                    suscripciones[index].id,
                                                    suscripcion
                                                )
                                                showDialog = false
                                            } catch (e: DateTimeParseException) {

                                                Toast.makeText(
                                                    context,
                                                    "Formato de fecha inválido. Usa el formato dd-MM-yyyy.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } catch (e: NumberFormatException) {
                                                Toast.makeText(
                                                    context,
                                                    "El precio debe ser un numero usando decimal con punto",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }, shape = RoundedCornerShape(50)

                                    ) {
                                        Text("Modificar")
                                    }

                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { showDialog = false },
                                        shape = RoundedCornerShape(50)
                                    ) {
                                        Text(
                                            "Cancelar", color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                },
                                containerColor = Color(0xFFffe8c0),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                    Button(onClick = {
                        viewModel.eliminarSuscripcion(suscripciones[index].id)
                    }) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}

@Composable
fun GenerarEstructuraFacturacion(texto1: String, texto2: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = texto1, fontSize = 20.sp)
        Text(text = texto2, fontSize = 20.sp)
    }
}
