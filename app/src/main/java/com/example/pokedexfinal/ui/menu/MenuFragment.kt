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
            findNavController().navigate(R.id.action_menuFragment_to_creditFragment)
        }
        binding.buttonExit.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_loginFragment)
        }
        binding.buttonPokeList.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_pokemonListFragment)
        }
        binding.buttonFavList.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_favPokemonListFragment)
        }

        binding.btnUser.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_userInfoFragment)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.bottom_List -> {
                    findNavController().navigate(R.id.action_menuFragment_to_pokemonListFragment)
                    true
                }

                R.id.bottom_Fav -> {
                    findNavController().navigate(R.id.action_menuFragment_to_favPokemonListFragment)
                    true
                }

                R.id.bottom_User -> {
                    findNavController().navigate(R.id.action_menuFragment_to_userInfoFragment)
                    true
                }
                else -> false
            }
        }


        return binding.root
    }

}
