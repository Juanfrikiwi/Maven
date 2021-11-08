package com.example.marvelcharacters.data.models

import com.google.gson.annotations.SerializedName

data class Image(
    @field:SerializedName("path") val path: String,
    @field:SerializedName("extension") val extension: String,
)
