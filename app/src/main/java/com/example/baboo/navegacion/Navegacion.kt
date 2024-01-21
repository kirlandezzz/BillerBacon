package com.example.baboo.navegacion

sealed class Navegacion(val ruta: String) {
    object PantallaBienvenida: Navegacion("PantallaBienvenida")
    object PantallaIniciarSesion: Navegacion("PantallaIniciarSesion")
}