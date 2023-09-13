package com.amonteiro.a23_11_sparks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a23_11_sparks.databinding.ActivityMainBinding


const val MENU_ID_WEATHER = 1

class MainActivity : AppCompatActivity() {

    //Instancier le XML à retardement (à la 1er utilisation de binding
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    //Callback Création de l'écran
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MainActivity.onCreate")

        //Afficher le composant
        setContentView(binding.root)

        //Clic sur valider
        binding.btValidate.setOnClickListener {
            if (binding.rbLike.isChecked) {
                binding.et.setText(binding.rbLike.text)
            }
            else if (binding.rbDislike.isChecked) {
                binding.et.setText(binding.rbDislike.text)
            }

            binding.iv.setImageResource(R.drawable.baseline_flag_24)
        }
        binding.btCancel.setOnClickListener {
            binding.rg.clearCheck()
        }

    }

    override fun onStart() {
        super.onStart()

        println("MainActivity.onStart")
    }

    override fun onStop() {
        super.onStop()
        println("MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MainActivity.onDestroy")
    }

    //Callback de la création du menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0,MENU_ID_WEATHER,0,"Météo")
        return super.onCreateOptionsMenu(menu)
    }

    //callback du clic sur le menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_ID_WEATHER -> {
                val intent = Intent(this, WeatherActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}