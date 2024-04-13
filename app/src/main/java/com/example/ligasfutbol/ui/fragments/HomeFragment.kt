package com.example.ligasfutbol.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.HomeFragmentBinding
import com.example.ligasfutbol.databinding.LoginFragmentBinding
import com.example.ligasfutbol.ui.activities.SecondActivity
import com.example.ligasfutbol.ui.adapter.LeagueAdapter
import com.example.ligasfutbol.ui.dialog.MessageDialog
import com.example.ligasfutbol.ui.model.League
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var LeagueAdapter : LeagueAdapter
    private lateinit var recyclerLeagues : RecyclerView


    //Método para pegar
    override fun onAttach (context: Context){
        super.onAttach(context)
    }

    //Método que muestra lo que hay en el fragment
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)

        //Método para importar y visualizar las ligas
        LeagueAdapter = LeagueAdapter(ArrayList(), requireContext(), "main")
        makeListLeagues()

        recyclerLeagues = binding.root.findViewById<RecyclerView>(R.id.recycler_leagues)
        recyclerLeagues.adapter = LeagueAdapter
        recyclerLeagues.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)


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
    fun makeListLeagues(){

        //Monto la petición
        val request : JsonObjectRequest = JsonObjectRequest(
            "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php",
            {
                //RESPUESTA DE DATOS
                //Lo que me devuelve la API
                val result : JSONArray = it.getJSONArray("leagues")

                //Recorro la información que tengo
                for(i in 0 until result.length()){

                    val element = result[i] as JSONObject

                    if(element.getString("strSport")=="Soccer") {
                        val league: League = League(element.getString("strLeague"))
                        LeagueAdapter.addElement(league)
                    }

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