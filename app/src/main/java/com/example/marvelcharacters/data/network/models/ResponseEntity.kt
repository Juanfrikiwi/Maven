package com.example.marvelcharacters.data.network.models

import com.example.marvelcharacters.data.AllCharactersEntity
import com.google.gson.annotations.SerializedName

data class ResponseEntity(
    @field:SerializedName("data") val data: AllCharactersEntity,
)

