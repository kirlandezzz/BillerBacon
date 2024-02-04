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

@RequiresApi(Build.VERSION_CODES.O)
class ViewModelMain : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _suscripciones = MutableStateFlow<List<Suscripcion>>(emptyList())
    val suscripciones: StateFlow<List<Suscripcion>> = _suscripciones

    init {
        cargarSuscripciones()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cargarSuscripciones() {
        viewModelScope.launch {
            db.collection("InfoPrueba")
                .get()
                .addOnSuccessListener { documentos ->
                    val listaSuscripciones = documentos.mapNotNull { doc ->
                        doc.toObject(Suscripcion::class.java).apply {
                            // Ajusta según tu clase y estructura de Firestore
                            this.fechaInicio = LocalDate.parse(doc.getString("fechaInicio"))
                            this.fechaCaducidad = LocalDate.parse(doc.getString("fechaCaducidad"))
                            this.precio = doc.getDouble("Precio")!!
                        }
                    }
                    _suscripciones.value = listaSuscripciones
                }
        }
    }
}
