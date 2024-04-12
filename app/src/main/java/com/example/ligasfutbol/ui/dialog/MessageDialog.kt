package com.example.ligasfutbol.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.ligasfutbol.R

class MessageDialog : DialogFragment() {

    private lateinit var context : Context //pantalla donde lo voy a crear
    private lateinit var button : Button
    private lateinit var view : View
    private lateinit var title : String
    private lateinit var text : String

    //Método que nos permite pasar parámetros entre la activity y el cuadro de diálogo
        fun newInstance(title : String, text : String) : MessageDialog{

            val args = Bundle()
            args.putString("title", title)
            args.putString("text", text)

            val dialogo = MessageDialog()
            dialogo.arguments=args
            return dialogo

        }

    //Método que pega el cuadro de diálogo
    override fun onAttach(context: Context){
        super.onAttach(context)
        this.context=context

        title= arguments?.getString("title") ?: ""
        text = arguments?.getString("text") ?: ""
    }

    //Método que me permite crear el cuadro de diálogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //Constructor del cuadro de diálogo
        var builder : AlertDialog.Builder = AlertDialog.Builder(context)

        //Pego la vista
        view = LayoutInflater.from(context).inflate(R.layout.message_dialog, null)
        view.findViewById<TextView>(R.id.labelMessageDialog_Title).text=title //título de la ventana
        view.findViewById<TextView>(R.id.labelMessageDialog_Text).text=text //texto
        builder.setView(view)

        //Inicializo el botón
        button = view.findViewById(R.id.buttonMessageDialog_OK)

        //Eventos de los botones
            //Botón aceptar -> que cierra el cuadro de diálogo
            button.setOnClickListener {

                dialog?.dismiss()
            }

        return builder.create()
    }

}