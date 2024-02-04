package com.example.billerbacon.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billerbacon.clases.Suscripcion
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

    fun cargarSuscripcionesDeUsuario(usuarioId: String) {
        viewModelScope.launch {
            db.collection("suscripciones")
                .whereEqualTo("UsuarioID", usuarioId) // Filtrar por el ID del usuario
                .get()
                .addOnSuccessListener { documentos ->
                    val listaSuscripciones = documentos.mapNotNull { documento ->
                        // Aquí, puedes seguir teniendo el mismo mapeo que antes
                        documento.toObject(Suscripcion::class.java).apply {
                            this.fechaInicio =
                                documento.getTimestamp("FechaInicio")?.toDate()?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())?.toLocalDate()
                                    ?: LocalDate.now()
                            this.fechaCaducidad =
                                documento.getTimestamp("FechaCaducidad")?.toDate()?.toInstant()
                                    ?.atZone(ZoneId.systemDefault())?.toLocalDate()
                                    ?: LocalDate.now()
                            this.precio = documento.getDouble("Precio") ?: 0.0
                            this.imagen = documento.getString("Imagen") ?: ""
                        }
                    }
                    _suscripciones.value = listaSuscripciones
                }
                .addOnFailureListener { exception ->
                    // Manejar error
                    println("Error al obtener las suscripciones: $exception")
                }
        }
    }

    fun agregarSuscripcionConUsuario(suscripcion: Suscripcion) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Suscripciones").add(suscripcion)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document $e")
            }
    }


}

