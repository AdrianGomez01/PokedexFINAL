package com.example.pokedexfinal.ui.favpokedetails

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
import com.example.pokedexfinal.databinding.FragmentFavDetailsBinding
import kotlinx.coroutines.launch

class FavPokeDetailsFragment : Fragment() {

    companion object {
        const val DRAWABLE = "drawable"
    }


    private var _binding: FragmentFavDetailsBinding? = null
    private val binding
        get() = _binding!!


    val args: FavPokeDetailsFragmentArgs by navArgs()

    private val favPokeDetailsVM by viewModels<FavPokeDetailsVM> { FavPokeDetailsVM.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavDetailsBinding.inflate(layoutInflater, container, false)

        favPokeDetailsVM.setPoke(args.idPoke)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabAdd.setOnClickListener{
           // ComentListFragment.addComent(coment)
        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favPokeDetailsVM.uiState.collect { pokeState ->
                    if(!pokeState.isLoading) {
                        binding.pbLoadingDetails.isVisible = false
                        pokeState.poke?.let {
                            binding.tvFavPokeNameDetails.text = it.name
                            Glide.with(requireContext()).load(it.photo).into(binding.ivFavPokeDetails)
                            binding.tvFavTipo1Det.text = it.type1
                            binding.tvFavTipo2Det.text = it.type2
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