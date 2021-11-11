package com.example.marvelcharacters.data

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @field:SerializedName("data") val data: AllCharactersEntity,
)

