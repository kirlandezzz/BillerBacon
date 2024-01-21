package com.example.baboo.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baboo.interfaces.PantallaBienvenida
import com.example.baboo.interfaces.PantallaIniciarSesion

@Composable
fun NavegacionHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navegacion.PantallaBienvenida.ruta) {
       composable(route = Navegacion.PantallaBienvenida.ruta) {
           PantallaBienvenida(navController)
       }
        composable(route = Navegacion.PantallaIniciarSesion.ruta) {
            PantallaIniciarSesion()
        }
    }
}