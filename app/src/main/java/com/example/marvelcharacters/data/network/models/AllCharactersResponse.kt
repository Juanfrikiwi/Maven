package com.example.marvelcharacters.data

import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.google.gson.annotations.SerializedName

data class AllCharactersResponse(
    @field:SerializedName("results") val characters: List<CharactersResponse>,
    @field:SerializedName("total") val totalPages: Int
)

