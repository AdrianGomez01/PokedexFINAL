package com.example.pokedexfinal.ui.coments

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexfinal.adapter.ComentsAdapter
import com.example.pokedexfinal.databinding.FragmentComentsBinding
import com.example.pokedexfinal.databinding.FragmentFavDetailsBinding
import com.example.pokedexfinal.datamodel.UserComents
import com.example.pokedexfinal.datamodel.UserPreferences
import com.example.pokedexfinal.ui.login.LoginVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class ComentListFragment : Fragment() {


    private var _binding: FragmentComentsBinding? = null
    val binding
        get() = _binding!!

    val args: ComentListFragmentArgs by navArgs()

    private val comentsVM by viewModels<ComentsVM> { ComentsVM.Factory }
    private val loginVM: LoginVM by viewModels<LoginVM> { LoginVM.Factory }


    private lateinit var comentsAdapter: ComentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentComentsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecView()

        setCollectors()

        setListeners()


    }

    private fun initRecView() {
        comentsAdapter = ComentsAdapter(
            _comentList = mutableListOf(),
        )
        binding.rvComents.adapter = comentsAdapter
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.rvComents.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        else {
            binding.rvComents.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                comentsVM.uiState.collect {
                    if (!it.isLoading) {
                        binding.pbLoading.isVisible = false
                        comentsAdapter.setComentsList(it.comentList)
                        comentsAdapter.notifyDataSetChanged()
                    }else {
                        binding.pbLoading.isVisible = true
                    }
                }
            }
        }
    }

    private fun setListeners() {

        binding.btnBackComents.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnGuardarComent.setOnClickListener{

            val comentario = UserComents(
                autor = loginVM.uiState.value.name,
                pokeId = args.idPoke,
                texto = binding.etIntroComent.text.toString()
            )

            comentsVM.addComent(comentario)
        }
    }





}