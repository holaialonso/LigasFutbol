package com.example.ligasfutbol.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.ActivitySecondBinding
import com.example.ligasfutbol.ui.adapter.LeagueAdapter
import com.example.ligasfutbol.ui.model.League
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SecondActivity : AppCompatActivity(), LeagueAdapter.onRecyclerLeagueListener {

    // private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondBinding
    private lateinit var database : FirebaseDatabase //servicio de base de datos
    private lateinit var idUser : String

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


        /*val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return true
    }

    /*   override fun onOptionsItemSelected(item: MenuItem): Boolean {
           // Handle action bar item clicks here. The action bar will
           // automatically handle clicks on the Home/Up button, so long
           // as you specify a parent activity in AndroidManifest.xml.
          /* return when (item.itemId) {
               R.id.action_settings -> true
               else -> super.onOptionsItemSelected(item)
           }*/
       }*/

    /* override fun onSupportNavigateUp(): Boolean {
         val navController = findNavController(R.id.nav_host_fragment_content_main)
         return navController.navigateUp(appBarConfiguration)
                 || super.onSupportNavigateUp()
     }*/


    //Método para obtener el nombre del usuario
    private fun setTitleMenu(idUser : String){

        var aux : String = ""
        val referencia = database.getReference("users").child(idUser).
        addListenerForSingleValueEvent(object : ValueEventListener {
            //Cuando un dato ha cambiado: me devuelve la foto del nodo por el que estoy preguntado
            override fun onDataChange(snapshot : DataSnapshot){
                aux= snapshot.child("name").value.toString()
                println("referencia bbdd dentro ->"+aux)
                binding.toolbarTitle.setText("Hola\n"+aux)

            }

            override fun onCancelled(error : DatabaseError){
            }
        })


    }

    //Métodos de la interfaz
    override fun onLeagueSelected(league: League) {

        println("Me voy al detalle de los equipos de la liga ->"+league.name)
    }
}