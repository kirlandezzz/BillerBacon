package com.example.billerbacon.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.billerbacon.interfaces.login.PantallaBienvenida
import com.example.billerbacon.interfaces.login.PantallaIniciarSesion
import com.example.billerbacon.interfaces.main.PantallaInicio
import com.example.billerbacon.interfaces.login.PantallaRegistro

@Composable
fun NavegacionHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navegacion.PantallaBienvenida.ruta) {
        composable(route = Navegacion.PantallaBienvenida.ruta) {
            PantallaBienvenida(navController)
        }
        composable(route = Navegacion.PantallaIniciarSesion.ruta) {
            PantallaIniciarSesion(navController)
        }
        composable(route = Navegacion.PantallaRegistro.ruta) {
            PantallaRegistro(navController)
        }
        composable(route = Navegacion.PantallaInicio.ruta) {
            PantallaInicio(navController)
        }
    }
}