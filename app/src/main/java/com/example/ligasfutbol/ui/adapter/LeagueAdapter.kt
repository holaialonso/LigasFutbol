package com.example.ligasfutbol.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ligasfutbol.R
import com.example.ligasfutbol.ui.model.League

class LeagueAdapter (private var leagues : ArrayList <League>, private val contexto : Context, private val activity : String): RecyclerView.Adapter<LeagueAdapter.MyHolder>() {

    //Listener de la clase
    private lateinit var listener: onRecyclerLeagueListener
    private lateinit var allLeagues : ArrayList<League>

    //Clase que pinta los valores de la lista
    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {

        // Elementos de la liga
        var name: TextView
        var button : LinearLayout


        init {
            name = item.findViewById(R.id.labelNameLeague)
            button = item.findViewById(R.id.buttonLeague)
        }

    }

    //Init
    init {
        listener = contexto as onRecyclerLeagueListener
        allLeagues = leagues
    }


    //GRÁFICA
        //Método que pinta para cada elemento de la lista la parte gráfica
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

            //Inicializo la vista por defecto (la del main)
            var vista: View = LayoutInflater.from(contexto)
                .inflate(R.layout.item_league, parent, false)

            return MyHolder(vista)
        }

        override fun getItemCount(): Int {
            return leagues.size
        }


        //Método que representa cada dato en una posición
        override fun onBindViewHolder(holder: MyHolder, position: Int) {

            var league = leagues[position]
            holder.name.text = league.name

            //Pulsaciones
                //Cuando pulsamos sobre una liga
                holder.button?.setOnClickListener() {
                    listener.onLeagueSelected(league)
                }


        }


    //DATOS
    //Método para añadir un elemento a la lista
    fun addElement(element : League){

        leagues.add(element)
        allLeagues = leagues
        notifyItemInserted(leagues.size-1)

    }

    //COMUNICACIÓN ADAPTER -> ACTIVITY/FRAGMENT
    //Interfaz para la comunicación entre el adaptador y la activity
    interface onRecyclerLeagueListener {

        fun onLeagueSelected(league: League)

    }


}

