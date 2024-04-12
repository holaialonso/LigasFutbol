package com.example.ligasfutbol.ui.helpers

import androidx.fragment.app.FragmentManager
import com.example.ligasfutbol.ui.dialog.MessageDialog

object Helpers {

    //Función para mostrar los mensajes de error
    fun showMessageDialog(title: String, text: String, fragmentManager: FragmentManager) {

        val dialogo : MessageDialog = MessageDialog().newInstance(title, text)
            dialogo.show(fragmentManager, null)

    }

    //Función para comprobar si el email es válido (sigue el patrón esperado)
    fun isValidEmail(email: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }


    //Función para comprobar si la contraseña tiene como mínimo 6 caracteres y es alfanumérica
    fun isValidPassword(password : String) : Boolean{

        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
        return password.length >= 6 && regex.matches(password)
    }



}