package com.example.pokedexfinal.ui.pokedetails

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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedexfinal.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch

class PokeDetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding!!


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

        pokeDetailsVM.setPoke(args.idPoke)

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
                            Glide.with(requireContext()).load(it.photo).circleCrop().into(binding.ivPokeDetails)
                            binding.tvTipo1Det.text = it.type1
                            binding.tvTipo2Det.text = it.type2
                            binding.tvAltura.text = it.altura
                            binding.tvPeso.text = it.peso
                        }
                    } else {
                        binding.pbLoadingDetails.isVisible = true
                    }
                }
            }
        }
    }
}