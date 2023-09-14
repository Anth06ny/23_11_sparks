package com.amonteiro.a23_11_sparks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.amonteiro.a23_11_sparks.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        observe()
    }

    fun observe(){
        model.data.observe(this){
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

    fun loadData(cityName:String){
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

}