package com.example.ligasfutbol.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.ActivitySecondBinding
import com.example.ligasfutbol.databinding.HomeFragmentBinding
import com.example.ligasfutbol.ui.adapter.LeagueAdapter
import com.example.ligasfutbol.ui.adapter.TeamsAdapter
import com.example.ligasfutbol.ui.fragments.HomeFragment
import com.example.ligasfutbol.ui.fragments.TeamsFragment
import com.example.ligasfutbol.ui.model.League
import com.example.ligasfutbol.ui.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class SecondActivity : AppCompatActivity(), LeagueAdapter.onRecyclerLeagueListener, TeamsAdapter.onRecyclerTeamsListener{

    // private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondBinding
    private lateinit var idUser : String
    private lateinit var database : FirebaseDatabase //servicio de base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializo la base de datos
        database = FirebaseDatabase.getInstance("https://ial-ligas24-default-rtdb.europe-west1.firebasedatabase.app/")

        //Binding
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Menú
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title= ""

        //Compruebo si tengo algún parámetro en el intent
        if (intent.hasExtra("idUser")) {
            idUser = intent.extras!!.getString("idUser").toString()
            setTitleMenu(idUser)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_home -> {

                //Si estoy en otro fragment -> vuelvo del otro fragment a la home
                if(!getCurrentTypeFragment(R.id.homeFragment)){
                    getCurrentFragment()?.findNavController()?.navigate(R.id.action_teamsFragment_to_homeFragment, null)
                }

                true;
            }

            R.id.menu_favorites -> {

                var bundle = Bundle()
                    bundle.putString("idUser", idUser)
                    bundle.putBoolean("isFavorite", true)

                //Si estoy en otro fragment diferente
                if(!getCurrentTypeFragment(R.id.teamsFragment)){
                    getCurrentFragment()?.findNavController()?.navigate(R.id.action_homeFragment_to_teamsFragment, bundle)
                }
                else{//Si estoy en la misma
                    getCurrentFragment()?.findNavController()?.navigate(R.id.action_teamsFragment_self, bundle)
                }

                true
            }

            R.id.menu_exit_session -> { //volver a la pantalla de login
                val intent : Intent = Intent (this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_exit_app -> { //cerrar la app
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

        return true
    }



    //Método para obtener el nombre del usuario
    private fun setTitleMenu(idUser : String){

        var aux : String = ""
        val referencia = database.getReference("users").child(idUser).
        addListenerForSingleValueEvent(object : ValueEventListener {
            //Cuando un dato ha cambiado: me devuelve la foto del nodo por el que estoy preguntado
            override fun onDataChange(snapshot : DataSnapshot){
                aux= snapshot.child("name").value.toString()

               // binding.toolbarTitle.setText("Hola\n"+aux)

            }

            override fun onCancelled(error : DatabaseError){
            }
        })


    }

    //Métodos de la interfaz
        //Método para ir de la home -> al fragment de equipos
        override fun onLeagueSelected(league: League) {

            var fragment=getCurrentFragment()

            //Cambio el fragmen al de equipos
            if ((fragment != null)&&(getCurrentTypeFragment(R.id.homeFragment))) {

                var bundle = Bundle()
                bundle.putString("nameLeague", league.name)
                bundle.putString("idUser", idUser)
                bundle.putBoolean("isFavorite", false)

                fragment.findNavController().navigate(R.id.action_homeFragment_to_teamsFragment, bundle)
            }

        }


        //Método para guardar el equipo como favorito del usuario
        override fun onTeamFavorite(team: Team) {

            //Guardo la parte relativa a la base de datos
            val referencia = database.getReference("users").child(idUser).child("favorite").child(team.id.toString()) //obtengo el nodo
            referencia.child("name").setValue(team.name)
            referencia.child("image").setValue(team.img)

        }

        //Método para eliminar un equipo favorito del usuario
        override fun onTeamNoFavorite(team: Team) {

            //Guardo la parte relativa a la base de datos
            val referencia = database.getReference("users").child(idUser).child("favorite").child(team.id.toString()) //obtengo el nodo
            referencia.child("name").setValue(null)

        }


        //Método para obtener el fragment que está actualmente cargado
        private fun getCurrentFragment() : Fragment? {

            // Obtengo el fragment actual
            val fragmentManager = supportFragmentManager
            val fragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_content_second)

            return fragment
        }


        //Método para saber qué tipo de fragment es
        private fun getCurrentTypeFragment(fragmentId : Int) : Boolean{

            var aux = false
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_second) as NavHostFragment
            val navController = navHostFragment.navController

            val homeFragmentId = fragmentId //fragmentid

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == homeFragmentId) {
                    aux = true
                }
            }

            println("fragmento ->"+aux)

            return aux

        }




}