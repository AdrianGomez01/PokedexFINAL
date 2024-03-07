package com.example.pokedexfinal.ui.credit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokedexfinal.MainActivity
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.FragmentCreditBinding


class CreditFragment : Fragment() {

    private var _binding: FragmentCreditBinding? = null
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

        //Cambio el título del toolbar al de la página actual
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.credits)

        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtengo una referencia al MainActivity y vinculo el botón a una constante
        val mainActivity = activity as MainActivity
        val btnLlamarFuncion = view.findViewById<Button>(R.id.buttonContact)

        // Configuro el clic del botón para llamar a la función en MainActivity
        btnLlamarFuncion.setOnClickListener {
            mainActivity.createMail()
        }
    }

}