package com.example.ligasfutbol.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ligasfutbol.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método para ejecutar cosas cuando ya la vista ha terminado de crearse
    override fun onViewCreated(view : View, savedInstanceState : Bundle?){

        super.onViewCreated(view, savedInstanceState)

        //Acción del botón cuando se hace login

        /*binding.botonLogin.setOnClickListener{

            //Lo que quiero comunicar -> parámetros
            var bundle = Bundle()
            bundle.putString("correo", binding.editCorreo.text.toString())

            //lógica del botón
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment, bundle) //-> cuando se ha hecho toda la lógica -> me voy al otro fragment
        }*/
    }

    //Método para despegar el fragment
    override fun onDetach(){
        super.onDetach();
    }
}



