package com.example.layout

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.layout.databinding.ActivityPokemonDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonDetailBinding
    private val apiService = PokeApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val pokemonName = intent.getStringExtra("POKEMON_NAME") ?: return
        fetchPokemonDetail(pokemonName)
    }

    private fun fetchPokemonDetail(name: String) {
        apiService.getPokemonDetail(name).enqueue(object : Callback<PokemonDetail> {
            override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
                if (response.isSuccessful) {
                    response.body()?.let { detail ->
                        displayDetail(detail)
                    }
                } else {
                    Toast.makeText(this@PokemonDetailActivity, "Failed to fetch detail", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                Toast.makeText(this@PokemonDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayDetail(detail: PokemonDetail) {
        binding.toolbarLayout.title = detail.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        binding.tvStats.text = "Height: ${detail.height}\nWeight: ${detail.weight}"
        
        binding.tvTypes.text = detail.types.joinToString(", ") { it.type.name.replaceFirstChar { char -> char.uppercase() } }
        binding.tvMoves.text = detail.moves.take(10).joinToString(", ") { it.move.name.replaceFirstChar { char -> char.uppercase() } }

        Glide.with(this)
            .load(detail.sprites.frontDefault)
            .into(binding.ivPokemonDetail)
    }
}
