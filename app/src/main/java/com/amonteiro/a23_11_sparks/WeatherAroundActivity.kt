package com.amonteiro.a23_11_sparks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.amonteiro.a23_11_sparks.databinding.ActivityWeatherAroundBinding

class WeatherAroundActivity : AppCompatActivity() {

    val binding by lazy { ActivityWeatherAroundBinding.inflate(layoutInflater) }

    val adapter = WindListAdapter()

    //Ma gestion des données
    var list = ArrayList<WindBean>().apply {
        add(WindBean(count++))
        add(WindBean(count++))
    }
    var count = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this, 2)

        refreshScreen()

        binding.btAdd.setOnClickListener {
            list.add(0, WindBean(count++))
            refreshScreen()
        }

        binding.btDelete.setOnClickListener {
            list.removeFirstOrNull()
            refreshScreen()
        }
    }

    fun refreshScreen() {
        //textView
        binding.textView.text = list.joinToString("\n") { it.speed.toString() }
        //recyclerView
        adapter.submitList(list.toList())

    }


}