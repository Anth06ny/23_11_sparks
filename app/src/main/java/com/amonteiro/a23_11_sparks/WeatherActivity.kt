package com.amonteiro.a23_11_sparks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amonteiro.a23_11_sparks.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class WeatherActivity : AppCompatActivity() {

    //IHM
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    //Donnée
    var data: WeatherBean? = null
    var errorMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {

            binding.progressBar.isVisible = true
            val cityName = binding.editText.text.toString()

            //Reset des données
            data = null
            errorMessage = ""

            refreshScreen()

            //Tache asynchrone
            versionThread(cityName)

        }
    }

    fun versionThread(cityName:String) {
        thread {
            try {
                //Chercher les données
                data = RequestUtils.loadWeather(cityName)

                //J'actualise sur le thread graphique
                runOnUiThread {
                    refreshScreen()
                    binding.progressBar.isVisible = false
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Une erreur est survenue : ${e.message}"
                runOnUiThread {
                    refreshScreen()
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    fun versionCoroutine(cityName:String) {
        lifecycleScope.launch(Dispatchers.Default) {
            //withContext attend que tous les launch termine
//            withContext(Dispatchers.Default) {
//                launch {  }
//                launch {  }
//            }
            try {
                //Chercher les données
                data = RequestUtils.loadWeather(cityName)
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "Une erreur est survenue : ${e.message}"
            }
            //J'actualise sur le thread graphique
            launch(Dispatchers.Main) {
                refreshScreen()
                binding.progressBar.isVisible = false
            }
        }
    }

    fun refreshScreen() {
        //V1
        binding.textView.text = "Il fait ${data?.main?.temp ?: "-"}° à ${data?.name ?: "-"} avec un vent de ${data?.wind?.speed ?: "-"} m/s"
        val imageName = data?.weather?.firstOrNull()?.icon
        if (!imageName.isNullOrBlank()) {
            Picasso.get().load("https://openweathermap.org/img/wn/$imageName@4x.png").into(binding.imageView)
        }
        else {
            binding.imageView.setImageDrawable(null)
        }

        //Cas d'erreur
        binding.tvError.text = errorMessage
        binding.tvError.isVisible = errorMessage.isNotBlank()

        //V2
//        val finalData = data
//        if(finalData !=null) {
//            binding.textView.text = "Il fait ${finalData.main.temp }° à ${finalData.name } avec un vent de ${finalData.wind.speed } m/s"
//        }
//        else {
//            binding.textView.text = "-"
//        }
//
//        //v3
//        data?.let {
//            binding.textView.text = "Il fait ${it.main.temp }° à ${it.name } avec un vent de ${it.wind.speed } m/s"
//        } ?: run  {
//            binding.textView.text = "-"
//        }


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
