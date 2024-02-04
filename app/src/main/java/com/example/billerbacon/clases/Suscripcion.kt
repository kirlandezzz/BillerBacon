package com.example.billerbacon.clases

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Suscripcion(imagen: String, nombre: String, fechaInicio: LocalDate, fechaCaducidad: LocalDate, precio: Double) {
    val imagen = imagen
    val nombre = nombre
    var fechaInicio = fechaInicio
    var fechaCaducidad = fechaCaducidad
    var precio = precio

    @RequiresApi(Build.VERSION_CODES.O)
    public fun calcularFecha(): Int {
        var diasRestantes: Long = 0
        val fechaActual = LocalDate.now()
        diasRestantes = ChronoUnit.DAYS.between(fechaActual, fechaCaducidad)
        return diasRestantes.toInt()
    }
}