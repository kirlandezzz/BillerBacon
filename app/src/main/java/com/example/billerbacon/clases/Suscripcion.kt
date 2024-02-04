package com.example.billerbacon.clases

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Suscripcion @RequiresApi(Build.VERSION_CODES.O) constructor(
    var imagen: String = "",
    var nombre: String = "",
    var fechaInicio: LocalDate,
    var fechaCaducidad: LocalDate,
    var precio: Double = 0.0,
    val usuarioId: String = "" // Referencia al ID del usuario
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", "", LocalDate.now(), LocalDate.now(), 0.0)

    @RequiresApi(Build.VERSION_CODES.O)
    public fun calcularFecha(): Int {
        var diasRestantes: Long = 0
        val fechaActual = LocalDate.now()
        diasRestantes = ChronoUnit.DAYS.between(fechaActual, fechaCaducidad)
        return diasRestantes.toInt()
    }
}


