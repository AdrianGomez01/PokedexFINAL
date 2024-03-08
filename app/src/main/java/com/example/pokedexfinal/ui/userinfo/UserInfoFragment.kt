package com.example.pokedexfinal.ui.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {
    private var _binding: FragmentUserInfoBinding? = null
    private val binding
        get() = _binding!!


    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserInfoBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.HintUsuario)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        userName = sharedViewModel.getUserName()

        binding.tvUserName.text = userName

        return binding.root
    }
}
