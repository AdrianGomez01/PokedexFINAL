package com.example.pokedexfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexfinal.R
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.databinding.FavPokemonItemBinding

class FavPokeAdapter(
    private var _favPokeList: MutableList<Pokemon>,
    private val onClickDelete: (Pokemon) -> Unit,
    private val onClickRoot: (Int) -> Unit,
) : RecyclerView.Adapter<FavPokeAdapter.FavPokeViewHolder>() {

    val favPokeList get() = _favPokeList

    class FavPokeViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavPokemonItemBinding.bind(view)

        fun bind(poke: Pokemon, onClickDelete: (Pokemon) -> Unit, onClickRoot: (Int) -> Unit) {
            binding.tvName.text = poke.name
            binding.tvid.text = poke.id.toString()
            val context = binding.ivPhoto.context

            Glide.with(context).load(poke.photo).into(binding.ivPhoto)
            binding.root.setOnClickListener {
                onClickRoot(poke.id)
                //Snackbar.make(it, "Has seleccionado a ${poke.name}.", Snackbar.LENGTH_SHORT).show()
            }

            binding.ivDelPoke.setOnClickListener {
                onClickDelete(poke)
                //Snackbar.make(it, "Has eliminado a ${poke.name} de tus favoritos.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavPokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavPokeViewHolder(layoutInflater.inflate(R.layout.fav_pokemon_item,parent,false))
    }

    override fun onBindViewHolder(holder: FavPokeViewHolder, position: Int) {
        holder.bind(_favPokeList[position],onClickDelete, onClickRoot)
    }

    override fun getItemCount(): Int {
        return _favPokeList.size
    }

    fun setPokeList(pokes : List<Pokemon>) {
        _favPokeList = pokes.toMutableList()
    }
}