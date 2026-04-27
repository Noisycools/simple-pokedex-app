package com.example.layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.layout.databinding.ItemPokemonBinding

class PokemonListAdapter(
    private val onClick: (PokemonResult) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var pokemonList: List<PokemonResult> = emptyList()

    fun submitList(list: List<PokemonResult>) {
        pokemonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: PokemonResult) {
            binding.tvName.text = pokemon.name
            Glide.with(binding.ivPokemon.context)
                .load(pokemon.getImageUrl())
                .into(binding.ivPokemon)

            binding.root.setOnClickListener {
                onClick(pokemon)
            }
        }
    }
}
