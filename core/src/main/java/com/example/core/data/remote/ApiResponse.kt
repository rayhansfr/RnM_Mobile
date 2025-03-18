package com.example.core.data.remote

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @field:SerializedName("results")
    val results: List<Characters>
)

data class Characters(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("species")
    val species: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("image")
    val image: String,

)


