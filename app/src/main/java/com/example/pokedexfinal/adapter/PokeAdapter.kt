package com.example.pokedexfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexfinal.R
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.PokemonItemBinding
import com.google.android.material.snackbar.Snackbar

class PokeAdapter(
    private var _pokeList: MutableList<Pokemon>,
    private val onClickAdd: (Pokemon) -> Unit,
    private val onClickRoot: (Int) -> Unit,
) : RecyclerView.Adapter<PokeAdapter.PokemonViewHolder>() {

    companion object {
        const val DRAWABLE = "drawable"
    }
    val pokeList get() = _pokeList

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val binding = PokemonItemBinding.bind(view)

        fun bind(pokemon: Pokemon, onClickAdd: (Pokemon) -> Unit, onClickRoot: (Int) -> Unit) {
            binding.tvName.text = pokemon.name
            binding.tvid.text = pokemon.id.toString()

            val context = binding.ivPhoto.context
            Glide.with(context).load(pokemon.photo).into(binding.ivPhoto)

            binding.root.setOnClickListener {
                onClickRoot(pokemon.id)
                //Snackbar.make(it, "Has seleccionado a ${pokemon.name}.", Snackbar.LENGTH_SHORT).show()
            }

            binding.ivFavPoke.setOnClickListener {
                onClickAdd(pokemon)
                //Snackbar.make(it, "Has añadido a ${pokemon.name} a tus favoritos.", Snackbar.LENGTH_SHORT).show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.pokemon_item, parent, false))
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }

    fun setPokeList(pokes : List<Pokemon>) {
        _pokeList = pokes.toMutableList()
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(_pokeList[position], onClickAdd, onClickRoot)
    }

}