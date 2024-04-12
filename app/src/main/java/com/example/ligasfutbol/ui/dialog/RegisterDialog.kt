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
import androidx.fragment.app.DialogFragment
import com.example.ligasfutbol.R

class RegisterDialog : DialogFragment ()  {

    private lateinit var context: Context //pantalla donde lo voy a crear
    private lateinit var name : String //nombre del usuario
    private lateinit var view : View
    private lateinit var buttonLogin : Button
    private lateinit var buttonExit : Button


    //Método necesario para pasar parámetros
   // companion object{

        fun newInstance(name : String) : RegisterDialog{

            val args = Bundle()
            args.putString("name", name)

            val dialogo = RegisterDialog()
            dialogo.arguments=args
            return dialogo
        }

   // }

    //Método para "pegar" el cuadro de diálogo
    override fun onAttach(context : Context ){

        super.onAttach(context)
        this.context=context
        this.name= this.arguments?.getString("name") ?: ""
    }


    //Método que me permite crear el cuadro de diálogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //Constructor del cuadro de diálogo
        var builder : AlertDialog.Builder = AlertDialog.Builder(this.context)

        //Pego la vista
        view = LayoutInflater.from(this.context).inflate(R.layout.register_dialog, null)
        view.findViewById<TextView>(R.id.labelRegisterDialog_Title).text="¡Hola "+this.name+"!"
        builder.setView(view)

        //Inicializar los botones
        buttonLogin = view.findViewById(R.id.buttonRegisterLogin)
        buttonExit = view.findViewById(R.id.buttonRegisterExit)

        return builder.create()
    }


    //Método para cuando ya tengo la vista creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //Botón que nos lleva al login (iniciar sesión)
        buttonLogin.setOnClickListener {  }

        //Botón que sale de la aplicación
        buttonExit.setOnClickListener {
            finishAff
        }

    }






}