package com.example.billerbacon.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billerbacon.R
import com.example.billerbacon.clases.Suscripcion
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class ViewModelMain : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _suscripciones = MutableStateFlow<List<Suscripcion>>(emptyList())
    val suscripciones: StateFlow<List<Suscripcion>> = _suscripciones

    fun cargarSuscripcionesDeUsuario(usuarioID: String) {
        db.collection("suscripciones")
            .whereEqualTo("usuarioID", usuarioID)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("ViewModelMain", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val listaSuscripciones = mutableListOf<Suscripcion>()
                snapshots?.forEach { documento ->
                    documento.toObject(Suscripcion::class.java)?.let { suscripcion ->
                        suscripcion.id = documento.id
                        suscripcion.fechaInicio = documento.getTimestamp("fechaInicio") ?: Timestamp.now()
                        suscripcion.fechaCaducidad = documento.getTimestamp("fechaCaducidad") ?: Timestamp.now()
                        suscripcion.precio = documento.getDouble("precio") ?: 0.0
                        suscripcion.imagen = documento.getString("imagen") ?: ""
                        listaSuscripciones.add(suscripcion)
                    }
                }
                _suscripciones.value = listaSuscripciones
            }
    }


    fun agregarSuscripcion(
        nombre: String,
        precio: Double,
        fechaInicio: Timestamp,
        fechaCaducidad: Timestamp,
        usuarioID: String
    ) {
        val logoResId = obtenerLogoPorNombre(nombre)

        val suscripcion = Suscripcion(
            id = "",
            imagen = nombre,
            nombre = nombre,
            fechaInicio = fechaInicio,
            fechaCaducidad = fechaCaducidad,
            precio = precio,
            usuarioID = usuarioID,
            logoResId = logoResId
        )

        db.collection("suscripciones").add(suscripcion)
            .addOnSuccessListener {
                Log.d("Firestore", "Suscripción añadida con éxito")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al añadir la suscripción", e)
            }
    }

    fun obtenerLogoPorNombre(nombre: String): Int {
        return when (nombre.lowercase()) {
            "netflix" -> R.drawable.netflix_new_icon
            "spotify" -> R.drawable.spotify_logo
            "amazon prime" -> R.drawable.amazon_prime_video
            else -> R.drawable.billerbacon
        }
    }


    fun eliminarSuscripcion(idSuscripcion: String) {
        db.collection("suscripciones").document(idSuscripcion)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Documento eliminado con ID: $idSuscripcion")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al eliminar documento", e)
            }
    }
    fun actualizarSuscripcion(suscripcionId: String, suscripcionActualizada: Suscripcion) {
        viewModelScope.launch {
            try {
                db.collection("suscripciones").document(suscripcionId)
                    .set(suscripcionActualizada)
                    .addOnSuccessListener {
                        Log.d("ViewModelMain", "Documento actualizado con éxito")
                        cargarSuscripcionesDeUsuario(suscripcionActualizada.usuarioID)
                    }
                    .addOnFailureListener { e ->
                        Log.w("ViewModelMain", "Error al actualizar el documento", e)
                    }
            } catch (e: Exception) {
                Log.e("ViewModelMain", "Error al actualizar suscripción", e)
            }
        }
    }
}

