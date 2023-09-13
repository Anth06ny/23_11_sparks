package com.amonteiro.a23_11_sparks

import java.util.Calendar
import java.util.Random
import kotlin.concurrent.thread

fun main() {
    PrintRandomIntBean(10)
    PrintRandomIntBean()

    val d1 = ""

}

fun toto(data : String){
}

/* -------------------------------- */
// API Weather
/* -------------------------------- */
//Ici je n'ai mis que ce qui est utile pour l'affichage demandé mais on peut tout mettre
data class WeatherBean(
    var name: String,
    var main: TempBean,
    var wind : WindBean,
    var weather:List<DescriptionBean>?
)

data class TempBean(var temp: Double)
data class WindBean(var speed: Double)
data class DescriptionBean(var icon: String)

/* -------------------------------- */
// EXO
/* -------------------------------- */

class RandomBean {
    private val list = arrayListOf("Titi", "Toto", "tata")
    private var oldValue = ""

    fun add(name : String?)= if(!name.isNullOrBlank() && name !in list) list.add(name) else false

    fun next() = list.random()

    fun nextDiff() =  list.filter { it != oldValue }.random().also {oldValue=it    }

    fun next2() = Pair(nextDiff(),nextDiff())

    fun thisMorning() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
    }.timeInMillis

}

class ThermometerBean(val min: Int, val max: Int, value: Int) {
    var value = value
        set(newValue) {
            field = if (newValue < min) min else if (newValue > max) max else newValue
        }
}


//beans.kt
class PrintRandomIntBean(val max: Int) {
    private val random = Random()

    init {
        println("init")
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }

    constructor() : this(100) {
        println("constructor")
        println(random.nextInt(max))
    }
}

//Ici color et area sont des attributs
//width et length sont des simples paramètres du constructeur
class HouseBean(var color: String, width: Int, length: Int) {
    var area = width * length

    fun print() = println("La maison $color fait ${area}m²")
}

class CarBean(var marque: String? = null, var model: String? = "") {

    //Différentes possibilités en fonction de si on veut une valeur nullable ou non
    var couleur = ""
    //var couleur : String? = ""
    //var couleur : String? = null
}


