package com.amonteiro.a23_11_sparks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a23_11_sparks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Instancier le XML à retardement (à la 1er utilisation de binding
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

}