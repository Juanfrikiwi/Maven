package com.example.marvelcharacters.data

import com.example.marvelcharacters.data.models.CharactersEntity
import com.google.gson.annotations.SerializedName

data class AllCharactersEntity(
    @field:SerializedName("results") val characters: List<CharactersEntity>,
    @field:SerializedName("total") val totalPages: Int
)

