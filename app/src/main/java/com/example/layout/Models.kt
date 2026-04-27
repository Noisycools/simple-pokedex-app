package com.example.layout

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
) {
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
    }
}

data class PokemonDetail(
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val moves: List<MoveSlot>,
    val sprites: Sprites
)

data class TypeSlot(
    val type: Type
)

data class Type(
    val name: String
)

data class MoveSlot(
    val move: Move
)

data class Move(
    val name: String
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)
