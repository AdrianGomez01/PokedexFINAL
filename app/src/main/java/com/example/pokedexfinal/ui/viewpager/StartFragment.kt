package com.example.pokedexfinal.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentStartBinding
import com.example.pokedexfinal.ui.login.LoginFragmentDirections

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.Enter)

        binding.btnStart.setOnClickListener {
            val action= ViewPagerFragmentDirections.actionViewPagerFragmentToMenuFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}