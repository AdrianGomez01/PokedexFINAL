package com.example.pokedexfinal.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pokedexfinal.adapter.PokeAdapter
import com.example.pokedexfinal.databinding.FragmentPokemonListBinding
import com.google.android.material.snackbar.Snackbar


class PokeListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    val binding
        get() = _binding!!

    val pokemons = Datasource.getPokemonList()

    private lateinit var pokemonAdapter: PokeAdapter
    private lateinit var layoutManager: LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Cambio el título del toolbar al de la página actual
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.pokemon_list)

        // Inflate the layout for this fragment
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)


        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.setListaPokemon(pokemons)

        initRecView()
        return binding.root
    }

    fun addFavPoke(pos: Int) {

        if (sharedViewModel.getListaPokemonFavoritos().contains(pokemons.get(pos))) {
            showSnackbar("Ya has añadido a " + pokemons.get(pos).name + " a tus Pokemon favoritos.")
        } else {
            showSnackbar("Has añadido a " + pokemons.get(pos).name + " a tus Pokemon favoritos.")
        }

        sharedViewModel.addFavourite(pokemons.get(pos).name)
    }

    private fun navigateToDetailFragment(pos: Int) {
        val selPokemon = pokemons.get(pos)
        sharedViewModel.setPokemon(selPokemon)
        findNavController().navigate(R.id.action_pokemonListFragment_to_detailItemFragment)
    }


    private fun initRecView() {
        pokemonAdapter = PokeAdapter(
            pokemons,
            { pos -> addFavPoke(pos) },
            { pos -> navigateToDetailFragment(pos) })
        binding.rvPokemons.adapter = pokemonAdapter

        //Insertar las siguientes lineas en un if que compruebe la orientacion del dispositivo y actuar en consecuencia
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvPokemons.layoutManager = layoutManager

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}