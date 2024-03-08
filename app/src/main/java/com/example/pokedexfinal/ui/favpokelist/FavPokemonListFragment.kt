package com.example.pokedexfinal.ui.favpokelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pokedexfinal.adapter.FavPokeAdapter
import com.example.pokedexfinal.databinding.FragmentFavPokemonListBinding


class FavPokemonListFragment : Fragment() {

    private var _binding: FragmentFavPokemonListBinding? = null
    val binding
        get() = _binding!!

    private var favPokemons = Datasource.getFavPokemonList()


    private lateinit var favPokemonAdapter: FavPokeAdapter
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
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fav_list)

        // Inflate the layout for this fragment
        _binding = FragmentFavPokemonListBinding.inflate(inflater, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        favPokemons = sharedViewModel.getListaPokemonFavoritos().toMutableList()

        initRecView()


        return binding.root
    }

    fun delFavPoke(pos: Int) {
        sharedViewModel.delFavourite(favPokemons.get(pos).name)
        favPokemons.removeAt(pos)
        binding.rvFavPokemons.adapter?.notifyItemRemoved(pos)
    }

    private fun navigateToFavDetailFragment(pos: Int) {
        val selPokemon = favPokemons.get(pos)
        sharedViewModel.setPokemon(selPokemon)
        findNavController().navigate(R.id.action_favPokemonListFragment_to_detailFavItemFragment)
    }

    private fun initRecView() {
        favPokemonAdapter = FavPokeAdapter(
            favPokemons,
            { pos -> delFavPoke(pos) },
            { pos -> navigateToFavDetailFragment(pos) })
        binding.rvFavPokemons.adapter = favPokemonAdapter

        //Insertar las siguientes lineas en un if que compruebe la orientacion del dispositivo y actuar en consecuencia
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFavPokemons.layoutManager = layoutManager

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}