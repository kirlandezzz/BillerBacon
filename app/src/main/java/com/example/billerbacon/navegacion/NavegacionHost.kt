package com.example.billerbacon.navegacion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.billerbacon.interfaces.login.PantallaBienvenida
import com.example.billerbacon.interfaces.login.PantallaIniciarSesion
import com.example.billerbacon.interfaces.main.PantallaInicio
import com.example.billerbacon.interfaces.login.PantallaRegistro
import com.example.billerbacon.interfaces.main.PantallaInformacion

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(route = Navegacion.PantallaInformacion.ruta) {
            PantallaInformacion(navController)
        }
    }
}