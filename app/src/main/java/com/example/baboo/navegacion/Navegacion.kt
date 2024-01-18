package com.example.baboo.navegacion

sealed class Navegacion(val ruta: String) {
    object pantallaBienvenida: Navegacion("PantallaBienvenida")
}