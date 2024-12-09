package com.example.billerbacon.interfaces.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.clases.Suscripcion
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
    val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f)
            ) {
                FloatingActionButton(
                    onClick = { },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 50.dp, bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Micrófono",
                        tint = Color.White
                    )
                }

                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },

            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFffe8c0))
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .zIndex(1f)
                )
                {
                    items(suscripciones) { item ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = painterResource(id = item.logoResId),
                                    contentDescription = "${item.nombre} Logo",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(start = 8.dp)
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = item.nombre,
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = String.format("%.2f €", item.precio),
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color.LightGray)
                                        .border(
                                            BorderStroke(2.dp, Color.White),
                                            RoundedCornerShape(30.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${item.calcularFecha()}",
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        style = TextStyle(
                                            shadow = Shadow(
                                                color = Color.Black,
                                                blurRadius = 6f,
                                            )
                                        ),
                                        textAlign = TextAlign.Center
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

                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = {
                                Text(
                                    text = "Nueva Suscripción",
                                    style = TextStyle(fontSize = 20.sp, color = Color.Black),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            },
                            text = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    OutlinedTextField(
                                        value = nombreSuscripcion,
                                        onValueChange = { nombreSuscripcion = it },
                                        label = { Text("Nombre de la suscripción") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    OutlinedTextField(
                                        value = precioSuscripcion,
                                        onValueChange = { precioSuscripcion = it },
                                        label = { Text("Precio") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        OutlinedTextField(
                                            value = fechaInicio,
                                            onValueChange = { fechaInicio = it },
                                            label = { Text("Fecha inicio (dd-MM-yyyy)") },
                                            singleLine = true,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(end = 8.dp)
                                        )

                                        OutlinedTextField(
                                            value = fechaCaducidad,
                                            onValueChange = { fechaCaducidad = it },
                                            label = { Text("Fecha caducidad (dd-MM-yyyy)") },
                                            singleLine = true,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp)
                                        )
                                    }
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

                                            viewModel.agregarSuscripcion(
                                                nombre = nombreSuscripcion,
                                                precio = precioParsed,
                                                fechaInicio = timestampInicio,
                                                fechaCaducidad = timestampCaducidad,
                                                usuarioID = usuarioID!!
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
                                                "El precio debe ser un número usando decimal con punto.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    },
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Guardar", fontSize = 16.sp)
                                }
                            },
                            dismissButton = {
                                OutlinedButton(
                                    onClick = { showDialog = false },
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Cancelar",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            containerColor = Color.White,
                            shape = RoundedCornerShape(16.dp)
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
