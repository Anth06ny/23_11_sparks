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
import com.amonteiro.a23_11_sparks.R
import com.amonteiro.a23_11_sparks.databinding.FragmentScreen12Binding
import com.amonteiro.a23_11_sparks.databinding.FragmentScreen22Binding

/**
 * A simple [Fragment] subclass.
 * Use the [Screen22Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Screen22Fragment : Fragment(), MenuProvider {

    private var _binding: FragmentScreen22Binding? = null
    private val binding get() = _binding!!

    //val model by lazy { ViewModelProvider(this)[Screen12ViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentScreen22Binding.inflate(inflater, container, false)

        //Menu
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.add(0,3,0, "Up")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == 3){
            findNavController().navigateUp()
            return true
        }
        return false
    }

}