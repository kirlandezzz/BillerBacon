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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.billerbacon.clases.Suscripcion
import com.example.billerbacon.navegacion.Navegacion
import com.example.billerbacon.viewmodels.ViewModelMain
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(navController: NavController? = null) {
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    var showDialog by remember { mutableStateOf(false) }
    var lista = ArrayList<Suscripcion>()
    val suscripcion = Suscripcion(
        imagen = "Xbox", nombre = "Game Pass",
        fechaInicio = LocalDate.of(2024, 1, 1), fechaCaducidad = LocalDate.of(2024, 3, 1),
        precio = 12.99
    )
    lista.add(suscripcion)
    val viewModel:ViewModelMain = viewModel()
    val suscripciones by viewModel.suscripciones.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                        Text(text = currentDate, color = Color.Black)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* handle navigation icon click */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFffe8c0)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
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
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
            ) {
                items(suscripciones) { item ->
                    Box(contentAlignment = Alignment.Center, modifier = Modifier
                        .width(400.dp)
                        .height(100.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White)
                        .border(BorderStroke(6.dp, Color.Gray), RoundedCornerShape(15.dp))
                        .clickable { }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "${item.imagen}   ")
                            Text(text = "${item.precio}   ")
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(Color.Gray)
                                    .width(50.dp)
                                    .wrapContentSize(Alignment.Center)
                                    .height(50.dp), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${item.calcularFecha()}    ", modifier = Modifier
                                        .wrapContentSize(Alignment.Center)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    fontSize = 25.sp,
                                    color = Color.White
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
                    .background(Color(0xFFffe8c0)), // Setting the background color
                contentAlignment = Alignment.Center
            ) {
                // Your content goes here, for instance, a list of items
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Dismiss")
                            }
                        },
                        title = { Text(text = "Add Subscription") },
                        text = { Text(text = "Fill in the details for the new subscription.") }
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
