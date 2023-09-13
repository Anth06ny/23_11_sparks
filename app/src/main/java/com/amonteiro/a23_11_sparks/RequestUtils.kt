package com.amonteiro.a23_11_sparks

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader

fun main() {
    val weather = RequestUtils.loadWeather("Toulouse")
    println("Il fait ${weather.main.temp}° à ${weather.name} avec un vent de ${weather.wind.speed} m/s")
}

object RequestUtils {
    val client = OkHttpClient()
    val gson = Gson()


    fun fakeRequest(): String {
        Thread.sleep(6000)
        return "Coucou"
    }

    fun loadWeather(city: String): WeatherBean {
       val json =  sendGet("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")
        return gson.fromJson(json, WeatherBean::class.java)
    }



    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }

    fun loadWeather() = sendGetOpti("").use {
        var isr = InputStreamReader(it.body.byteStream())
        gson.fromJson(isr, WeatherBean::class.java)
    }

    fun sendGetOpti(url: String): Response {
        println("url : $url")
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Réponse du serveur incorrect : ${response.code}")
        }
        return response //.body.string()
    }

}