package com.example.pokedexfinal.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    private val settingsVM : SettingsVM by viewModels<SettingsVM> { SettingsVM.Factory }

    var skipWelcome = false
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
        binding = FragmentSettingsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()
        setListeners()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsVM.uiState.collect {
                    binding.etUserName.setText(settingsVM.uiState.value.name)
                    skipWelcome = it.skipWelcome
                    binding.cbSkipWelcomeSettings.isChecked = it.skipWelcome
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnBackSettings.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSaveSettings.setOnClickListener {
            skipWelcome = binding.cbSkipWelcomeSettings.isChecked
            validateName(binding.etUserName.text.toString())
            Snackbar.make(requireView(),getString(R.string.settings_saves),Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun validateName(name: String) {
        if(name.isBlank())
            Snackbar.make(requireView(),getString(R.string.no_name),Snackbar.LENGTH_SHORT).show()
        else
            settingsVM.saveSettings(name,skipWelcome)
    }


}