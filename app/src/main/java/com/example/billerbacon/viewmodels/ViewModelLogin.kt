package com.example.billerbacon.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.internal.RecaptchaActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.launch

class ViewModelLogin : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _cargando = MutableLiveData(false)
    private val _mensajeError = MutableLiveData<String?>()
    val mensajeError: LiveData<String?> get() = _mensajeError

    fun iniciarSesion(email: String, clave: String, home: () -> Unit) = viewModelScope.launch {

        try {
            auth.signInWithEmailAndPassword(email, clave).addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    home()
                } else {

                    _mensajeError.value = "Correo electrónico o clave incorrectos"
                }
            }
        } catch (ex: FirebaseAuthInvalidCredentialsException) {

            _mensajeError.value = "Las credenciales proporcionadas son incorrectas o han expirado"
            Log.d("BillerBacon", "IniciarSesion: Credenciales inválidas - ${ex.message}")

        } catch (ex: FirebaseTooManyRequestsException) {
            _mensajeError.value = "Se ha bloqueado el acceso temporalmente debido a demasiados intentos fallidos. Intente de nuevo más tarde."
        } catch (ex: Exception) {

            _mensajeError.value =
                "Error en el inicio de sesión: ${ex.message ?: "Error desconocido"}"
            Log.d("BillerBacon", "IniciarSesion: ${ex.message}")
        }
    }


    fun registrarUsuario(email: String, clave: String, home: () -> Unit) {
        if (_cargando.value == false) {
            _cargando.value == true
            auth.createUserWithEmailAndPassword(email, clave).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val displayName = task.result?.user?.email?.split("@")?.get(0) ?: "User"
                    crearUsuario(displayName)
                    home()
                } else {
                    Log.d("BillerBacon", "registrarUsuario: ${task.result.toString()}")
                }
                _cargando.value == false
            }
        }
    }

    private fun crearUsuario(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
        FirebaseFirestore.getInstance().collection("users").add(user)
            .addOnSuccessListener { Log.d("BillerBacon", "crearUsuario: Creado usuario ${it.id}") }
            .addOnFailureListener { Log.d("BillerBacon", "crearUsuario: Fallida la creacion ${it}")}
    }
}