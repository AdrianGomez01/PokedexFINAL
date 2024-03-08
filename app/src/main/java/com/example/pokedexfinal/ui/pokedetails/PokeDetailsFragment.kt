package com.example.pokedexfinal.ui.pokedetails

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedexfinal.R
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.FragmentDetailsBinding
import com.example.pokedexfinal.databinding.FragmentFavPokemonListBinding
import com.example.pokedexfinal.databinding.FragmentPokemonListBinding
import kotlinx.coroutines.launch

class PokeDetailsFragment : Fragment() {

    companion object {
        const val DRAWABLE = "drawable"
    }


    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!


    //TODO PREGUNTAR POR QUE NO PILLA EL ARGS (1)
    val args: PokeDetailsFragmentArgs by navArgs()

    private val pokeDetailsVM by viewModels<PokeDetailsVM> { PokeDetailsVM.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.details)

        //TODO PREGUNTAR POR QUE NO PILLA EL ARGS (2)
        pokeDetailsVM.setPoke(args.idPoke)

        //TODO
       // sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        //pokemon = sharedViewModel.getPokemon()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokeDetailsVM.uiState.collect { pokeState ->
                    if(!pokeState.isLoading) {
                        binding.pbLoadingDetails.isVisible = false
                        pokeState.poke?.let {
                            binding.tvPokeNameDetails.text = it.name
                            //binding.ivPhoto.setImageResource(requireContext().resources.getIdentifier("iron_man", DRAWABLE,requireContext().packageName))
                            Glide.with(requireContext()).load(it.photo).circleCrop().into(binding.ivPokeDetails)
                            binding.tvTipo1Det.text = it.type1
                            binding.tvTipo2Det.text = it.type2
                            binding.tvDescriptionDetails.text = it.description
                        }
                    } else {
                        binding.pbLoadingDetails.isVisible = true
                    }
                }
            }
        }
    }
}