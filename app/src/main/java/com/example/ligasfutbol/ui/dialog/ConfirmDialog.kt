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
import com.example.ligasfutbol.R
import com.example.ligasfutbol.ui.adapter.TeamsAdapter

class ConfirmDialog : DialogFragment(){

    private lateinit var context : Context //pantalla donde lo voy a crear
    private lateinit var buttonYes : Button
    private lateinit var buttonNo : Button
    private lateinit var view : View
    private lateinit var title : String
    private lateinit var text : String
    private lateinit var type : String //tipo de la accion
    private lateinit var listener : ConfirmDialog.onConfirmDialogListener

    //Init


    //Método que nos permite pasar parámetros entre la activity y el cuadro de diálogo
    fun newInstance(title : String, text : String, type : String) : ConfirmDialog{

        val args = Bundle()
        args.putString("title", title)
        args.putString("text", text)
        args.putString("type", type)

        val dialogo = ConfirmDialog()
        dialogo.arguments=args
        return dialogo

    }

    //Método que pega el cuadro de diálogo
    override fun onAttach(context: Context){
        super.onAttach(context)
        this.context=context
        this.listener = context as ConfirmDialog.onConfirmDialogListener

        title= arguments?.getString("title") ?: ""
        text = arguments?.getString("text") ?: ""
        type = arguments?.getString("type") ?: ""
    }


    //Método que me permite crear el cuadro de diálogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //Constructor del cuadro de diálogo
        var builder : AlertDialog.Builder = AlertDialog.Builder(context)

        //Pego la vista
        view = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null)
        view.findViewById<TextView>(R.id.labelConfirmDialog_Title).text=title //título de la ventana
        view.findViewById<TextView>(R.id.labelConfirmDialog_Text).text=text //texto
        builder.setView(view)

        //Inicializo los botones
        buttonYes = view.findViewById(R.id.buttonConfirmYes)
        buttonNo = view.findViewById(R.id.buttonConfirmNo)

        //Eventos de los botones
        //Botón aceptar
        buttonYes.setOnClickListener {
            listener.onAcceptAction(type)
        }

        //Botón cancelar
        buttonNo.setOnClickListener {
            dialog?.dismiss()
        }

        // Impedir que el diálogo se cierre al tocar fuera
        var dialog : Dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }


    //INTERFAZ
    interface onConfirmDialogListener{

        fun onAcceptAction(type : String){}


    }


}