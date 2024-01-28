package com.example.billerbacon.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.launch

//ViewModel para gestinar el inicio de sesión
class ViewModelLogin: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _cargando = MutableLiveData(false)

    //Función necesaria para validar el usuario y la contraseña
    fun iniciarSesion(email: String, clave: String, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, clave).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("BillerBacon", "IniciarSesion: Un usuario se ha logeado en la app")
                    home()
                } else {
                    Log.d("BillerBacon", "IniciarSesion: ${task.result.toString()}")
                }
            }
        }catch (ex: Exception) {
            Log.d("BillerBacon", "IniciarSesion: ${ex.message}")
        }
    }
}