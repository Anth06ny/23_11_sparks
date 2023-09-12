package com.amonteiro.a23_11_sparks

import android.widget.Button
import kotlin.concurrent.thread

fun main() {

    println("#${scanNombre("Donne moi un nombre : ")}#")

}

fun scantText(texte : String): String {
    print(texte)
    return readlnOrNull() ?: "-"
}

fun scanNombre(texte :String) = scantText(texte).toIntOrNull() ?: -1

fun toto(a:Int = 5) {

}