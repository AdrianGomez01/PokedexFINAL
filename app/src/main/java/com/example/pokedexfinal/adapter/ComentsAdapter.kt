package com.example.pokedexfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexfinal.R
import com.example.pokedexfinal.databinding.ComentItemBinding
import com.example.pokedexfinal.datamodel.UserComents

class ComentsAdapter(
    private var _comentList: MutableList<UserComents>,
) : RecyclerView.Adapter<ComentsAdapter.ComentsViewHolder>() {

    val comentList get() = _comentList

    class ComentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ComentItemBinding.bind(view)

        fun bind(coment: UserComents) {
            binding.tvUserComent.text = coment.autor
            binding.tvComentText.text = coment.texto
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComentsViewHolder(layoutInflater.inflate(R.layout.coment_item, parent, false))
    }

    override fun getItemCount(): Int {
        return _comentList.size
    }

    override fun onBindViewHolder(holder: ComentsViewHolder, position: Int) {
        holder.bind(_comentList[position])
    }

    fun setComentsList(coment: List<UserComents>) {
        _comentList = coment.toMutableList()
    }



}