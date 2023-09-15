package com.amonteiro.a23_11_sparks

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amonteiro.a23_11_sparks.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class WeatherActivity : AppCompatActivity() {

    //IHM
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    //Data
    val model by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //callback du click
        binding.btLoad.setOnClickListener {
            val cityName = binding.editText.text.toString()
            model.loadData(cityName)

        }

        binding.btLoadPosition.setOnClickListener {
            //test de la permission
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                model.loadData(this)
            }
            //je n'ai pas la permission
            else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0
                )
            }
        }
        observe()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            model.loadData(this)
        }
        //je n'ai pas la permission
        else {
            //Afficher une erreur
            model.setErrorMessage("Il faut la permission")
        }
    }

    fun observe() {
        model.data.observe(this) {
            binding.textView.text = "Il fait ${it?.main?.temp ?: "-"}° à ${it?.name ?: "-"} avec un vent de ${it?.wind?.speed ?: "-"} m/s"
            val imageName = it?.weather?.firstOrNull()?.icon
            if (!imageName.isNullOrBlank()) {
                Picasso.get().load("https://openweathermap.org/img/wn/$imageName@4x.png").into(binding.imageView)
            }
            else {
                binding.imageView.setImageDrawable(null)
            }
        }

        model.errorMessage.observe(this) {
            binding.tvError.text = it
            binding.tvError.isVisible = it.isNotBlank()
        }

        model.runInProgress.observe(this) {
            binding.progressBar.isVisible = it
        }
    }
}

class WeatherViewModel : ViewModel() {
    var data = MutableLiveData<WeatherBean?>(null)
    var errorMessage = MutableLiveData("")

    var runInProgress = MutableLiveData(false)


    //Charge les données à partir de la localisation
    fun loadData(context: Context) {
        //Reset donnée
        data.postValue(null)
        errorMessage.postValue("")
        runInProgress.postValue(true)

        thread {
            try {
                val location = LocationUtils.getLastKnownLocation(context) ?: throw Exception("Aucune localisation trouvée")

                //Chercher les données
                data.postValue(RequestUtils.loadWeather(location.latitude, location.longitude))
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }
            runInProgress.postValue(false)
        }
    }

    //Charg les données à partir d'un nom de ville
    fun loadData(cityName: String) {
        //Reset donnée
        data.postValue(null)
        errorMessage.postValue("")
        runInProgress.postValue(true)

        thread {
            try {
                //Chercher les données
                data.postValue(RequestUtils.loadWeather(cityName))
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }
            runInProgress.postValue(false)
        }
    }

    fun setErrorMessage(newErrorMessage: String?) {
        errorMessage.postValue(newErrorMessage)
    }

}