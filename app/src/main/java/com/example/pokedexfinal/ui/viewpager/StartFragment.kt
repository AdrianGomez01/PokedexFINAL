package com.example.pokedexfinal.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentStartBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding
        get() = _binding!!

    private val startVM: StartVM by viewModels<StartVM> { StartVM.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private fun setListeners() {

        binding.btnStart.setOnClickListener {

            //Snackbar.make(requireView(),binding.cbSkipWelcome.isChecked.toString(),Snackbar.LENGTH_SHORT).show()

            startVM.saveSettingsWelcome(binding.cbSkipWelcome.isChecked)

        }
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                startVM.uiState.collect {

                    if (it.savedSkipWelcome) {
                        //Snackbar.make(requireView(),it.skipWelcome.toString(),Snackbar.LENGTH_SHORT).show()
                        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToMenuFragment()
                        findNavController().navigate(action)
                    }
                    if (it.error){
                        Snackbar.make(requireView(),getString(R.string.error),Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.Enter)

        setListeners()
        setCollectors()

        return binding.root
    }
}