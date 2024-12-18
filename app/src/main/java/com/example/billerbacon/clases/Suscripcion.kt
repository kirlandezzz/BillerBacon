package com.example.billerbacon.clases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.billerbacon.R
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class Suscripcion @RequiresApi(Build.VERSION_CODES.O) constructor(
    var id: String = "",
    var imagen: String = "",
    var nombre: String = "",
    var fechaInicio: Timestamp,
    var fechaCaducidad: Timestamp,
    var precio: Double = 0.0,
    val usuarioID: String = "",
    val logoResId: Int = R.drawable.billerbacon
) {
    @RequiresApi(Build.VERSION_CODES.O)
    constructor() : this("", "", "", Timestamp.now(), Timestamp.now(), 0.0)

    @RequiresApi(Build.VERSION_CODES.O)
    public fun calcularFecha(): Int {
        var diasRestantes: Long = 0
        val fechaActual = LocalDate.now()
        val fechaCaducidadLocal = fechaCaducidad.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        diasRestantes = ChronoUnit.DAYS.between(fechaActual, fechaCaducidadLocal)
        if (diasRestantes < 0) {
            diasRestantes = 0
        }
        return diasRestantes.toInt()
    }
}


