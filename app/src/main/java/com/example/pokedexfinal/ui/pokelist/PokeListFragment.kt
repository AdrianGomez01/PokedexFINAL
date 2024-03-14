package com.example.pokedexfinal.ui.pokelist

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        // Inflate the layout for this fragment
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun addFavPoke(poke: Pokemon) {
        pokeListVM.saveFavPoke(poke)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokeListVM.uiState.collect { pokeState ->
                    if (pokeListVM.uiState.value.isFav) {
                        Snackbar.make(requireView(), "${poke.name} Ya pertenece a tus favoritos.", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(requireView(), "Has añadido a ${poke.name} a tus favoritos.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun selectPoke(pokeId: Int) {
        val action = PokeListFragmentDirections.actionPokeListFragmentToPokeDetailsFragment(pokeId)
        findNavController().navigate(action)
    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecView()

        setCollectors()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecView() {
        pokeAdapter = PokeAdapter(
            _pokeList = mutableListOf(),
            onClickAdd = { pokemon -> addFavPoke(pokemon) },
            onClickRoot = { pokeId -> selectPoke(pokeId) }
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

}