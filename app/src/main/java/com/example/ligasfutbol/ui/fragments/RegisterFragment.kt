package com.example.ligasfutbol.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.LoginFragmentBinding
import com.example.ligasfutbol.databinding.RegisterFragmentBinding
import com.example.ligasfutbol.ui.dialog.MessageDialog
import com.example.ligasfutbol.ui.dialog.RegisterDialog
import com.example.ligasfutbol.ui.helpers.Helpers
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment  : Fragment (){

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var auth : FirebaseAuth //servicio de autenticación
    private lateinit var database : FirebaseDatabase //servicio de base de datos
    private lateinit var title : String //para los mensajes de error
    private lateinit var text : String //para los mensajes de error

    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método para ejecutar cosas cuando ya la vista ha terminado de crearse
    override fun onViewCreated(view : View, savedInstanceState : Bundle?){

        super.onViewCreated(view, savedInstanceState)

        //Inicializo la autenticación y la base de datos
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://ial-ligas24-default-rtdb.europe-west1.firebasedatabase.app/")

        //Botón de registro
        binding.buttonRegister.setOnClickListener {
            makeRegisterUser()
        }
    }

    //Método para despegar el fragment
    override fun onDetach(){
        super.onDetach();
    }


    //Métodos del frament
        //Método para registrar a un usuario
        private fun makeRegisterUser(){

            //Obtengo todos los valores del formulario
            var name : String = binding.inputName.text.toString()
            var surname : String = binding.inputSurname.text.toString()
            var email : String = checkEmail(binding.inputEmailRegister.text.toString())
            var password : String = checkPassword(binding.inputPasswordRegister.text.toString())

            //Todos los campos tienen que estar rellenados
            if((!name.isNullOrEmpty())&&(!surname.isNullOrEmpty())&&(!email.isNullOrEmpty())&&(!password.isNullOrEmpty())){

                //Registro el usuario
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) { //la petición se ha resuelto de forma satistactoria

                        makeBBDDUser(auth.currentUser!!.uid, name, surname)

                        //Mensaje de confirmación
                        val dialogo: RegisterDialog = RegisterDialog().newInstance(name, email, password)
                           dialogo.show(parentFragmentManager, null)

                    } else { //error
                       Helpers.showMessageDialog("Error", "Ha ocurrido un error inesperado al intentar crear el usuario.\nInténtalo de nuevo.", parentFragmentManager)
                    }
                }
            }
            else {
               Helpers.showMessageDialog("Error", "¡Cuidado debes rellenar todos los campos del formulario", parentFragmentManager)
            }
        }


        //Método para guardar el usuario en la base de datos
        private fun makeBBDDUser(id : String, name : String, surname : String){

            //1. Obtengo el nodo
                val referencia = database.getReference("users").child(id)

            //2. Inserto los valores del nodo
                referencia.child("name").setValue(name)
                referencia.child("surname").setValue(surname)

        }


        //Método para comprobar el email
        private fun checkEmail(email : String) : String{

            var aux = email

            if(!Helpers.isValidEmail(aux)){
                aux=""

                //Mensaje de error
                title = "Error"
                text = "El email que has introducido no es correcto.\nRevisa que cumple con el patrón: usuario@servidor.com."
                Helpers.showMessageDialog(title, text, parentFragmentManager)

            }

            return aux
        }

        //Método para comprobar la contraseña
        private fun checkPassword(password : String) : String{
            var aux = password

            if(!Helpers.isValidPassword(password)){
                aux=""

                //Mensaje de error
                title="Error"
                text="La contraseña debe ser alfanumérica y tener, al menos, 6 caracteres."
                Helpers.showMessageDialog(title, text, parentFragmentManager)
            }

            return aux
        }



}