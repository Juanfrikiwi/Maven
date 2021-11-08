package com.example.marvelcharacters.data

import com.example.marvelcharacters.data.models.Characters
import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @field:SerializedName("characters") val characters: List<Characters>,
    @field:SerializedName("total_pages") val totalPages: Int
)

