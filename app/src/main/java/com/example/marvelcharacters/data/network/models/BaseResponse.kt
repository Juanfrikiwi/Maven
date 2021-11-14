package com.example.marvelcharacters.data.network.models

import com.example.marvelcharacters.data.AllCharactersResponse
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @field:SerializedName("data") val data: AllCharactersResponse,
)

