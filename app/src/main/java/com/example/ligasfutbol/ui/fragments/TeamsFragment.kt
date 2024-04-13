package com.example.ligasfutbol.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.HomeFragmentBinding
import com.example.ligasfutbol.databinding.TeamsFragmentBinding
import com.example.ligasfutbol.ui.adapter.LeagueAdapter
import com.example.ligasfutbol.ui.adapter.TeamsAdapter
import com.example.ligasfutbol.ui.model.League
import com.example.ligasfutbol.ui.model.Team
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class TeamsFragment : Fragment (){

    private lateinit var binding: TeamsFragmentBinding
    private lateinit var nameLeague : String
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var recyclerTeams : RecyclerView
    private lateinit var database : FirebaseDatabase //servicio de base de datos
    private lateinit var idUser : String
    private var isFavorite : Boolean = false

    private lateinit var textNoFavorites : View


    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)

        //Inicializo la base de datos
        database = FirebaseDatabase.getInstance("https://ial-ligas24-default-rtdb.europe-west1.firebasedatabase.app/")


        //Obtengo el nombre de la liga
        nameLeague= arguments?.getString("nameLeague").toString()
        idUser=arguments?.getString("idUser").toString()
        isFavorite= arguments?.getBoolean("isFavorite") == true
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = TeamsFragmentBinding.inflate(inflater, container, false)

        // Método para importar y visualizar las ligas
        teamsAdapter = TeamsAdapter(ArrayList(), requireContext(), "main")


        if (!isFavorite){
            binding.root.findViewById<TextView>(R.id.labelTeams_Title).setText("Equipos")
            binding.root.findViewById<TextView>(R.id.labelTeams_Subtitle).setText(nameLeague)
            makeLisTeams()
        }
        else{
            binding.root.findViewById<TextView>(R.id.labelTeams_Title).setText("Favoritos")
            binding.root.findViewById<TextView>(R.id.labelTeams_Subtitle).setText("Lo que has destacado")
            makeListFavTeams()
        }

        // Encuentra la RecyclerView dentro de binding.root
        recyclerTeams = binding.root.findViewById<RecyclerView>(R.id.recycler_teams)

        // Configura el adaptador y el layoutManager de la RecyclerView
        recyclerTeams.adapter = teamsAdapter
        recyclerTeams.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        textNoFavorites=binding.root.findViewById<TextView>(R.id.textNoFavorites)

        return binding.root
    }

    //Método para ejecutar cosas cuando ya la vista ha terminado de crearse
    override fun onViewCreated(view : View, savedInstanceState : Bundle?){

        super.onViewCreated(view, savedInstanceState)
    }

    //Método para despegar el fragment
    override fun onDetach(){
        super.onDetach();
    }


    //Método para consultar a la API las ligas
    private fun makeLisTeams(){

        //Monto la petición
        val request : JsonObjectRequest = JsonObjectRequest(
            "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l="+ URLEncoder.encode(nameLeague, "UTF-8"),
            {
                //RESPUESTA DE DATOS
                //Lo que me devuelve la API
                val result : JSONArray = it.getJSONArray("teams")

                //Recorro la información que tengo
                for(i in 0 until result.length()){
                    val element = result[i] as JSONObject
                    var team : Team = Team(element.getString("idTeam").toInt(), element.getString("strTeam"), element.getString("strStadiumThumb"), false)
                    teamsAdapter.addElement(team)
                }
            },
            {
                //ERROR
                Snackbar.make(binding.root, "Error en la conexion", Snackbar.LENGTH_SHORT).show();

            })

        //Hago la petición
        Volley.newRequestQueue(requireContext()).add(request)


    }

    //Método para sacar los equipos favoritos del usuario
    private fun makeListFavTeams(){

        val referencia = database.getReference("users").child(idUser).child("favorite")
        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->

                    // Obtener los valores de la base de datos
                    var name : String = dataSnapshot.child("name").getValue(String::class.java).toString()
                    var image : String = dataSnapshot.child("image").getValue(String::class.java).toString()
                    var id : Int = dataSnapshot.child("id").getValue(Int::class.java)?.toInt() ?: 0

                    //Crear los equipos y pasarlos al recycler
                    var team : Team = Team (id, name, image, true)
                    teamsAdapter.addElement(team)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores en caso de cancelación
            }
        })


    }


    private fun showRecyclerTeams(){

        println("teams -> "+teamsAdapter.getItemCount())

        if(teamsAdapter.getItemCount()==0) {
            textNoFavorites.visibility = View.VISIBLE
        }
        else{
           textNoFavorites.visibility = View.GONE

        }
    }


}