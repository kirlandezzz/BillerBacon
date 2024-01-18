package com.example.baboo.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baboo.interfaces.PantallaBienvenida

@Composable
fun NavegacionHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navegacion.pantallaBienvenida.ruta) {
       composable(route = Navegacion.pantallaBienvenida.ruta) {
           PantallaBienvenida()
       }
    }
}