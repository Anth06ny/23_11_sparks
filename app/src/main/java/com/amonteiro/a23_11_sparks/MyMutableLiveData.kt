package com.amonteiro.a23_11_sparks

import androidx.appcompat.app.AppCompatActivity


fun main() {

    val mutableData = MyMutableLiveData<String?>()

    mutableData.value = "tata"

    mutableData.observator = {
        println(it)
    }

    mutableData.value = "toto"

}

class MyMutableLiveData<T>(value:T? = null) {

    var value = value
        set(newValue) {
            field = newValue
            observator?.invoke(newValue)
        }

//    fun postValue(newValue:T?){
//        runOnUiThread {
//            value = newValue
//        }
//    }


    var observator : ((T?)-> Unit)? = null
        set(newValue) {
            field = newValue
            observator?.invoke(value)
        }
}