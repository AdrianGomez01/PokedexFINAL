package com.example.pokedexfinal.ui.pokelist

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.pokedexfinal.R
import com.example.pokedexfinal.adapter.PokeAdapter
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.FragmentPokemonListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class PokeListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    val binding
        get() = _binding!!

    private val pokeListVM by viewModels<PokeListVM> { PokeListVM.Factory }

    private lateinit var pokeAdapter: PokeAdapter

    // Guarda el estado de la lista de pokemons
    private var pokeListState: MutableList<Pokemon> = mutableListOf()

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


        return binding.root
    }

    fun addFavPoke(pos: Int) {

        /* if (sharedViewModel.getListaPokemonFavoritos().contains(pokemons.get(pos))) {
             showSnackbar("Ya has añadido a " + pokemons.get(pos).name + " a tus Pokemon favoritos.")
         } else {
             showSnackbar("Has añadido a " + pokemons.get(pos).name + " a tus Pokemon favoritos.")
         }

         sharedViewModel.addFavourite(pokemons.get(pos).name)*/
    }

    private fun selectPoke(pokeId: String) {
        //TODO - Por que me pide que retire el argumento
        val action = PokeListFragmentDirections.actionPokeListFragmentToPokeDetailsFragment()
        findNavController().navigate(action)
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecView()

        setCollectors()
    }

    private fun initRecView() {
        pokeAdapter = PokeAdapter(
            _pokeList = mutableListOf(),
            onClickAdd = { pos -> addFavPoke(pos) },
            onClickRoot = { pokeId -> selectPoke(pokeId.toString()) }
        )
        binding.rvPokemons.adapter = pokeAdapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.rvPokemons.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        else {
            binding.rvPokemons.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokeListVM.uiState.collect { pokeState ->
                    if (!pokeState.isLoading) {
                        binding.pbLoading.isVisible = false
                        pokeAdapter.setPokeList(pokeState.pokeList)
                        pokeAdapter.notifyDataSetChanged()
                    } else {
                        binding.pbLoading.isVisible = true
                    }

                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}