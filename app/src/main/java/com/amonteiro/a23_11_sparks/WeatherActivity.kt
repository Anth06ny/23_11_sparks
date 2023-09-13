package com.amonteiro.a23_11_sparks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amonteiro.a23_11_sparks.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class WeatherActivity : AppCompatActivity() {

    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {

            binding.progressBar.isVisible = true
            val cityName = binding.editText.text.toString()

            //Tache asynchrone
            thread {
                //Chercher les données
                val data = RequestUtils.loadWeather(cityName)

                //J'actualise sur le thread graphique
                runOnUiThread {
                    binding.textView.text = "Il fait ${data.main.temp}° à ${data.name} avec un vent de ${data.wind.speed} m/s"

                    binding.progressBar.isVisible = false
                    val imageName = data.weather?.firstOrNull()?.icon
                    if(!imageName.isNullOrBlank()){
                        Picasso.get().load("https://openweathermap.org/img/wn/$imageName@4x.png").into(binding.imageView)
                    }
                }
            }
        }
    }

}
//THREAD UI / MAIN THREAD
//affichage graphique
//animation du bouton
//onclick
//lancer thread 2
//animation du bouton
//mettre à jour graphique


//    binding.textView.text = "Il fait ${weather.main.temp}° à ${weather.name} avec un vent de ${weather.wind.speed} m/s"

//-------------------------------
//Thread 2
//    val weather = RequestUtils.loadWeather("Toulouse")
// envoyer runOnUiThread{} à l'UIThread
