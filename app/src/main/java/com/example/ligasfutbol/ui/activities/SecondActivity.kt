package com.example.ligasfutbol.ui.activities

import android.os.Bundle
import android.text.Html
import android.view.Gravity
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ligasfutbol.R
import com.example.ligasfutbol.databinding.ActivityMainBinding
import com.example.ligasfutbol.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    // private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title= ""

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
}