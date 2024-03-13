package com.example.pokedexfinal.ui.favpokelist

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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexfinal.R
import com.example.pokedexfinal.adapter.FavPokeAdapter
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.FragmentFavPokemonListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class FavPokemonListFragment : Fragment() {

    private var _binding: FragmentFavPokemonListBinding? = null
    val binding
        get() = _binding!!

    private val favPokeListVM by viewModels<FavPokemonListVM> { FavPokemonListVM.Factory }

    private lateinit var favPokemonAdapter: FavPokeAdapter


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
        _binding = FragmentFavPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun delFavPoke(poke: Pokemon) {
        favPokeListVM.delFavPoke(poke)


        Snackbar.make(requireView(), "Has eliminado a ${poke.name} de tus favoritos.", Snackbar.LENGTH_SHORT).show()
    }

    private fun confirmDeletePoke(poke: Pokemon) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete))
            .setMessage(resources.getString(R.string.support_confirm_delete))

            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
                favPokeListVM.delFavPoke(poke)
            }
            .show()
    }

    private fun selectPoke(pokeId: Int) {
        val action = FavPokemonListFragmentDirections.actionFavPokemonListFragmentToFavPokeDetailsFragment2(pokeId)
        findNavController().navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecView()

        setCollectors()

        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun initRecView() {
        favPokemonAdapter = FavPokeAdapter(
            _favPokeList = mutableListOf(),
            onClickDelete = { poke -> confirmDeletePoke(poke) },
            onClickRoot = { pokeId -> selectPoke(pokeId) }
        )
        binding.rvFavPokemons.adapter = favPokemonAdapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.rvFavPokemons.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        else {
            binding.rvFavPokemons.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favPokeListVM.uiState.collect { pokeState ->
                    if (!pokeState.isLoading) {
                        binding.pbLoading.isVisible = false
                        favPokemonAdapter.setPokeList(pokeState.pokeList)
                        favPokemonAdapter.notifyDataSetChanged()
                    } else {
                        binding.pbLoading.isVisible = true
                    }

                }
            }
        }
    }
}