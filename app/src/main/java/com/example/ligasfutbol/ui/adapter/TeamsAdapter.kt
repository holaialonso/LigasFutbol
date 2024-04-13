package com.example.ligasfutbol.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ligasfutbol.R
import com.example.ligasfutbol.ui.model.League
import com.example.ligasfutbol.ui.model.Team

class TeamsAdapter  (private var teams : ArrayList <Team>, private val contexto : Context, private val activity : String): RecyclerView.Adapter<TeamsAdapter.MyHolder>() {

    //Listener de la clase
    private lateinit var listener: TeamsAdapter.onRecyclerTeamsListener
    private lateinit var allTeams : ArrayList<Team>

    //Clase que pinta los valores de la lista
    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {

        // Elementos de la liga
        var name: TextView
        var image : ImageView
        var button : ImageButton


        init {
            name = item.findViewById(R.id.labelNameTeam)
            image = item.findViewById(R.id.imgTeam)
            button = item.findViewById(R.id.buttonFavoriteTeam)
        }

    }

    //Init
    init {
        listener = contexto as TeamsAdapter.onRecyclerTeamsListener
        allTeams = teams
    }

    //GRÁFICA
        //Método que pinta para cada elemento de la lista la parte gráfica
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapter.MyHolder {

            //Inicializo la vista por defecto (la del main)
            var vista: View = LayoutInflater.from(contexto)
                .inflate(R.layout.item_team, parent, false)

            return TeamsAdapter.MyHolder(vista)
        }

        override fun getItemCount(): Int {
            return teams.size
        }


        //Método que representa cada dato en una posición
        override fun onBindViewHolder(holder: TeamsAdapter.MyHolder, position: Int) {

            var team = teams[position]
            //holder.name.text = team.name

            //Pulsaciones de los botones


        }

    //DATOS
        //Método para añadir un elemento a la lista
        fun addElement(element : Team){

            teams.add(element)
            allTeams = teams
            notifyItemInserted(teams.size-1)

        }

    //COMUNICACIÓN ADAPTER -> ACTIVITY/FRAGMENT
        //Interfaz para la comunicación entre el adaptador y la activity
        interface onRecyclerTeamsListener {



        }
}