package com.example.ligasfutbol.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ligasfutbol.R
import com.example.ligasfutbol.ui.model.Team
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase


class TeamsAdapter  (private var teams : ArrayList <Team>, private val contexto : Context, private val activity : String): RecyclerView.Adapter<TeamsAdapter.MyHolder>() {

    //Listener de la clase
    private lateinit var listener: TeamsAdapter.onRecyclerTeamsListener
    private lateinit var allTeams : ArrayList<Team>


    //Clase que pinta los valores de la lista
    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {

        // Elementos de la liga
        var name: TextView
        var image : ImageView
        var buttonFav : ImageButton
        var buttonNoFav : ImageButton


        init {
            name = item.findViewById(R.id.labelNameTeam)
            image = item.findViewById(R.id.imgTeam)
            buttonFav = item.findViewById(R.id.buttonFavoriteTeam)
            buttonNoFav = item.findViewById(R.id.buttonDefavoriteTeam)
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
            holder.name.text = team.name

            //Si no tengo la imagen -> que no la cargue
            if(team.img!="null"){
                Glide.with(contexto).load(team.img).into(holder.image)
            }

            //Cambio los botones de las estrellas dependiendo de si el usuario tiene favoritos o no
            if(!team.favorite){
                holder.buttonFav.visibility = View.VISIBLE
                holder.buttonNoFav.visibility = View.GONE
            }
            else{
                holder.buttonFav.visibility = View.GONE
                holder.buttonNoFav.visibility = View.VISIBLE
            }

            //Pulsaciones de los botones
            //Botón favorito
                holder.buttonFav?.setOnClickListener() {
                    listener.onTeamFavorite(team)
                    team.favorite=true
                    updateList(team, position)
                }

            //Botón para eliminar el favorito
                holder.buttonNoFav?.setOnClickListener(){
                    listener.onTeamNoFavorite(team)
                    removeElement(position)
                }

        }

    //DATOS
        //Método para añadir un elemento a la lista
        fun addElement(element : Team){

            teams.add(element)
            allTeams = teams
            notifyItemInserted(teams.size-1)

        }

        //Método para actualizar un elemento del recycled
        fun updateList(element : Team, position: Int){
            println("dentro de update list")
            teams[position]=element
            allTeams=teams
            notifyItemChanged(position)
        }

        //Método para eliminar un elemento
        fun removeElement(position : Int){

            //Compruebo antes de eliminar el elemento que la lista no la tengo vacía y que no estoy intentando acceder a un elemento de ésta que no exista
            if ((teams.isNotEmpty())&&(position in 0 until teams.size)) {
                teams.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, teams.size)
            }

        }

    //COMUNICACIÓN ADAPTER -> ACTIVITY/FRAGMENT
        //Interfaz para la comunicación entre el adaptador y la activity
        interface onRecyclerTeamsListener {

            fun onTeamFavorite(team : Team)

            fun onTeamNoFavorite(team : Team)
        }
}