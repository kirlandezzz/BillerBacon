package com.example.billerbacon.interfaces.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.R
import com.example.billerbacon.clases.Suscripcion
import com.example.billerbacon.navegacion.Navegacion
import com.example.billerbacon.viewmodels.ViewModelMain
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PantallaInicio(navController: NavController) {
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    var showDialog by remember { mutableStateOf(false) }
    val viewModel: ViewModelMain = viewModel()
    val suscripciones by viewModel.suscripciones.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val context = LocalContext.current
    val fecha = LocalDate.parse("10-02-2024", formatter)
    val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
    var paddingNumeros: Dp
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedSubscriptionForDeletion by remember { mutableStateOf<Suscripcion?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(usuarioID) {
        usuarioID?.let { id ->
            viewModel.cargarSuscripcionesDeUsuario(id)
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier
                .background(Color(0xFFffe8c0))
                .fillMaxHeight()
                .width(150.dp)
            ) {

                DrawerItem(label = "Inicio", onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController?.navigate("PantallaInicio")

                })
                DrawerItem(label = "Créditos", onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                    navController?.navigate("PantallaCreditos")
                })
            }

        }
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(text = currentDate, color = Color.Black)
                }
            }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.Black)
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFffe8c0)
            )
            )
        }, floatingActionButton = {
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
                            .border(
                                BorderStroke(6.dp, Color.LightGray),
                                RoundedCornerShape(15.dp)
                            )
                            .combinedClickable(
                                onClick = { navController?.navigate("PantallaInformacion") },
                                onLongClick = {
                                    selectedSubscriptionForDeletion = item
                                    showDeleteDialog = true
                                }
                            )
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
                                    Text(text = "${item.precio}€")
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
                                        val fechaInicioParsed =
                                            LocalDate.parse(fechaInicio, formatter)
                                                .atStartOfDay(ZoneId.systemDefault()).toInstant()
                                        val fechaCaducidadParsed =
                                            LocalDate.parse(fechaCaducidad, formatter)
                                                .atStartOfDay(ZoneId.systemDefault()).toInstant()
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
                                        viewModel.agregarSuscripcion(suscripcion)
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
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Eliminar suscripción") },
                        text = { Text("¿Estás seguro de que quieres eliminar esta suscripción?") },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.eliminarSuscripcion(selectedSubscriptionForDeletion!!.id)
                                showDeleteDialog = false
                            }) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showDeleteDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun DrawerItem(label: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = label)
    }
}
