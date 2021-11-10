package com.example.marvelcharacters.data

import com.example.marvelcharacters.data.models.CharactersEntity
import com.google.gson.annotations.SerializedName

data class CharactersResponseEntity(
    @field:SerializedName("result") val characters: List<CharactersEntity>,
    @field:SerializedName("total") val totalPages: Int
)

