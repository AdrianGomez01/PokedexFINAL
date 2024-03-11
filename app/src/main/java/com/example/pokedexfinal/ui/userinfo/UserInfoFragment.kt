package com.example.pokedexfinal.ui.userinfo

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
import com.example.pokedexfinal.databinding.FragmentUserInfoBinding
import com.example.pokedexfinal.ui.login.LoginVM
import com.example.pokedexfinal.ui.viewpager.ViewPagerFragmentDirections
import kotlinx.coroutines.launch

class UserInfoFragment : Fragment() {

    private lateinit var binding: FragmentUserInfoBinding

    private val loginVM: LoginVM by viewModels<LoginVM> { LoginVM.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserInfoBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.HintUsuario)

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
                    binding.tvUserName.text = it.name
                }
            }
        }
    }


    private fun setListeners() {
        binding.fabSettings.setOnClickListener {
            val action = UserInfoFragmentDirections.actionUserInfoFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }

}
