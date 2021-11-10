package com.example.marvelcharacters.data

import com.example.marvelcharacters.data.models.CharactersEntity
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @field:SerializedName("data") val data: CharactersResponseEntity,
)

