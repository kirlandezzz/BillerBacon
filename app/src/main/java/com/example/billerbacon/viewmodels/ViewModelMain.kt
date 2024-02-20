package com.example.billerbacon.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun agregarSuscripcion(suscripcion: Suscripcion) {
        db.collection("suscripciones").add(suscripcion)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Documento agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error al agregar documento", e)
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

}

