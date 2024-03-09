package com.example.pokedexfinal.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var drawerLayoutMenu: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)

        drawerLayoutMenu = binding.drawerLayoutMenu

        //Cambio el título del toolbar al de la página actual
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.menu)

        binding.buttonCredits.setOnClickListener {
            val action= MenuFragmentDirections.actionMenuFragmentToCreditFragment()
            findNavController().navigate(action)
        }
        binding.buttonExit.setOnClickListener {
            val action= MenuFragmentDirections.actionMenuFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        binding.buttonPokeList.setOnClickListener {
            val action= MenuFragmentDirections.actionMenuFragmentToPokeListFragment()
            findNavController().navigate(action)
        }
        binding.buttonFavList.setOnClickListener {
            val action= MenuFragmentDirections.actionMenuFragmentToFavPokemonListFragment()
            findNavController().navigate(action)
        }

        binding.btnUser.setOnClickListener {
            val action= MenuFragmentDirections.actionMenuFragmentToUserInfoFragment()
            findNavController().navigate(action)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.bottom_List -> {
                    val action= MenuFragmentDirections.actionMenuFragmentToPokeListFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.bottom_Fav -> {
                    val action= MenuFragmentDirections.actionMenuFragmentToFavPokemonListFragment()
                    findNavController().navigate(action)
                    true
                }

                R.id.bottom_User -> {
                    val action= MenuFragmentDirections.actionMenuFragmentToUserInfoFragment()
                    findNavController().navigate(action)
                    true
                }
                else -> false
            }
        }


        return binding.root
    }

}
