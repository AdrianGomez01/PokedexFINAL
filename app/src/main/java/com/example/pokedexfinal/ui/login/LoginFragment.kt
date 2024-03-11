package com.example.pokedexfinal.ui.login

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
import com.example.pokedexfinal.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding

    private val loginVM: LoginVM by viewModels<LoginVM> { LoginVM.Factory }

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

        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        setListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollectors()

    }

    private fun setCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginVM.uiState.collect {
                    binding.etUserName.setText(it.name)
                    skipWelcome = it.skipWelcome
                }
            }
        }
    }

    private fun setListeners() {

        binding.buttonEnter.setOnClickListener {
            validateName(
                binding.etUserName.text.toString()
            )

            Snackbar.make(requireView(),loginVM.uiState.value.skipWelcome.toString(),Snackbar.LENGTH_SHORT).show()
            if (skipWelcome) {
                val action = LoginFragmentDirections.actionLoginFragmentToMenuFragment()
                findNavController().navigate(action)
            } else {
                val action = LoginFragmentDirections.actionLoginFragmentToViewPagerFragment()
                findNavController().navigate(action)
            }

        }
    }

    private fun validateName(name: String) {
        if (name.isBlank())
            Snackbar.make(requireView(), getString(R.string.no_name), Snackbar.LENGTH_SHORT).show()
        else
            loginVM.saveSettings(name, skipWelcome)
    }
}