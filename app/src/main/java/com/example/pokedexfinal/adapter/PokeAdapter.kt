package com.example.pokedexfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexfinal.R
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.PokemonItemBinding
import com.google.android.material.snackbar.Snackbar

class PokeAdapter(
    private var _pokeList: MutableList<Pokemon>,
    private val onClickAdd: (Int) -> Unit,
    private val onClickRoot: (Int) -> Unit,
) : RecyclerView.Adapter<PokeAdapter.PokemonViewHolder>() {

    companion object {
        const val DRAWABLE = "drawable"
    }
    val pokeList get() = _pokeList

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val binding = PokemonItemBinding.bind(view)

        fun bind(pokemon: Pokemon, onClickAdd: (Int) -> Unit, onClickRoot: (Int) -> Unit) {
            binding.tvName.text = pokemon.name
            binding.tvType1.text = pokemon.type1
            binding.tvType2.text = pokemon.type2
            val context = itemView.context
            binding.ivPhoto.setImageResource(
                context.resources.getIdentifier(
                    pokemon.photo,
                    DRAWABLE,
                    context.packageName
                )
            )

            binding.root.setOnClickListener {
                onClickRoot(adapterPosition)
                Snackbar.make(it, "Has seleccionado a ${pokemon.name}.", Snackbar.LENGTH_SHORT)
                    .show()
            }

            binding.ivFavPoke.setOnClickListener {
                onClickAdd(adapterPosition)
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