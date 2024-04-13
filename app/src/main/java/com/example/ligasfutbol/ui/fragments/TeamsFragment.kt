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
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class TeamsFragment : Fragment () {

    private lateinit var binding: TeamsFragmentBinding
    private lateinit var nameLeague : String
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var recyclerTeams : RecyclerView



    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)

        //Obtengo el nombre de la liga
        nameLeague= arguments?.getString("nameLeague").toString()
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = TeamsFragmentBinding.inflate(inflater, container, false)

        // Método para importar y visualizar las ligas
        teamsAdapter = TeamsAdapter(ArrayList(), requireContext(), "main")
        makeLisTeams()

        //Cambio el nombre de la liga
        binding.root.findViewById<TextView>(R.id.labelTeams_Subtitle).setText(nameLeague)

        // Encuentra la RecyclerView dentro de binding.root
        recyclerTeams = binding.root.findViewById<RecyclerView>(R.id.recycler_teams)

        // Configura el adaptador y el layoutManager de la RecyclerView
        recyclerTeams.adapter = teamsAdapter
        recyclerTeams.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)


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
    fun makeLisTeams(){

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
                    var team : Team = Team(element.getString("strTeam"), element.getString("strStadiumThumb"), false)
                    println(team)
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

}