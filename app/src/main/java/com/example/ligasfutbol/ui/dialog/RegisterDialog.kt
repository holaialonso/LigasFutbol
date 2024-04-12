package com.example.ligasfutbol.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R

class RegisterDialog : DialogFragment ()  {

    private lateinit var context: Context //pantalla donde lo voy a crear
    private lateinit var name : String //nombre del usuario
    private lateinit var email : String //email del usuario
    private lateinit var password : String //password del usuario
    private lateinit var view : View
    private lateinit var buttonLogin : Button
    private lateinit var buttonExit : Button


    //Método necesario para pasar parámetros
    fun newInstance(name : String, email : String, password: String) : RegisterDialog{

        val args = Bundle()
        args.putString("name", name)
        args.putString("email", email)
        args.putString("password", password)

        val dialogo = RegisterDialog()
        dialogo.arguments=args
        return dialogo
    }


    //Método para "pegar" el cuadro de diálogo
    override fun onAttach(context : Context ){

        super.onAttach(context)
        this.context =context
        name= arguments?.getString("name") ?: ""
        email = arguments?.getString("email") ?: ""
        password = arguments?.getString("password") ?: ""

    }


    //Método que me permite crear el cuadro de diálogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //Constructor del cuadro de diálogo
        var builder : AlertDialog.Builder = AlertDialog.Builder(context)

        //Pego la vista
        view = LayoutInflater.from(context).inflate(R.layout.register_dialog, null)
        view.findViewById<TextView>(R.id.labelRegisterDialog_Title).text="¡Hola "+name+"!"
        builder.setView(view)

        //Inicializar los botones
        buttonLogin = view.findViewById(R.id.buttonRegisterLogin)
        buttonExit = view.findViewById(R.id.buttonRegisterExit)

        //Eventos de los botones
            //Botón que nos lleva al login (iniciar sesión)
            buttonLogin.setOnClickListener {
                //Paso parámetros a la página de login
                var bundle : Bundle = Bundle()
                bundle.putString("email", email)
                bundle.putString("password", password)

                //Paso al login
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment, bundle)

                //Cierro el cuadro de diálogo
                dialog?.dismiss()
            }

            //Botón que sale de la aplicación
            buttonExit.setOnClickListener {
                activity?.finishAffinity()
            }

        return builder.create()
    }









}