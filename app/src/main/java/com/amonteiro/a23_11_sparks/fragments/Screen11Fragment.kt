package com.amonteiro.a23_11_sparks.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.amonteiro.a23_11_sparks.ExoNavGraphActivity
import com.amonteiro.a23_11_sparks.R
import com.amonteiro.a23_11_sparks.databinding.FragmentScreen11Binding


class Screen11Fragment : Fragment(), MenuProvider {

    private var _binding: FragmentScreen11Binding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Screen11Fragment.onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("Screen11Fragment.onCreateView")
        _binding = FragmentScreen11Binding.inflate(inflater, container, false)

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.STARTED)

        binding.btGoto.setOnClickListener {
            findNavController().navigate(R.id.action_screen11Fragment_to_screen12Fragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    /* -------------------------------- */
    // Menu
    /* -------------------------------- */

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0,2,0, "Goto 1.2")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == 2){
            findNavController().navigate(R.id.action_screen11Fragment_to_screen12Fragment)
            return true
        }
        return false
    }

}