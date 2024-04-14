package com.example.ligasfutbol.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.LoginFragmentBinding
import com.example.ligasfutbol.ui.activities.SecondActivity
import com.example.ligasfutbol.ui.dialog.MessageDialog
import com.example.ligasfutbol.ui.helpers.Helpers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var auth : FirebaseAuth //servicio de autenticación
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var title : String //para los avisos
    private lateinit var text : String //para los avisos

    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)

        //En caso de tenerlos, recojo los valores que me pasa desde el formulario de registro
        email = arguments?.getString("email") ?: ""
        password = arguments?.getString("password") ?: ""
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        //Inicializo la autenticación
        auth=FirebaseAuth.getInstance()

        //Si tengo los parámetros de email y password -> los pongo en los campos
        if((email.length>0)&&(password.length>0)){
            binding.inputEmail.setText(email)
            binding.inputPassword.setText(password)
        }
        return binding.root
    }

    //Método para ejecutar cosas cuando ya la vista ha terminado de crearse
    override fun onViewCreated(view : View, savedInstanceState : Bundle?){

        super.onViewCreated(view, savedInstanceState)

        //Botón para hacer el login
        binding.buttonLogin.setOnClickListener {

            //Obtengo los valores del email y el password y hago el login
            makeLogin(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())

        }

        //Botón de registro
        binding.buttonRegisterLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }


    }

    //Método para despegar el fragment
    override fun onDetach(){
        super.onDetach();
    }


    //FIREBASE
        //Método para hacer el login del usuario
        private fun makeLogin(email: String, password: String) {

            var idUser : String = ""

            //Hacemos el login
            if((!email.isNullOrEmpty())&&(!password.isNullOrEmpty())){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        idUser=auth.currentUser!!.uid //me da el usuario que está actualmente logueado en la aplicación
                        val intent : Intent = Intent (requireContext(), SecondActivity::class.java)
                            intent.putExtra("idUser", idUser)
                            startActivity(intent)

                    }
                    else{
                        title = "Error"
                        text = "Se ha producido un error al iniciar sesión en el sistema con el usuario y contraseña introducidos."
                        val dialogo : MessageDialog = MessageDialog().newInstance(title, text)
                            dialogo.show(parentFragmentManager, null)
                    }
                }
            }
            else{
                title = "Error"
                text = "Debes rellenar el email y la contraseña."
                val dialogo : MessageDialog = MessageDialog().newInstance(title, text)
                dialogo.show(parentFragmentManager, null)
            }

        }



}



