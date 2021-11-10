package com.example.marvelcharacters.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersEntity(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("modified") val modified: Date,
    @field:SerializedName("resourceURI") val resourceURI: String,
    @field:SerializedName("urls") val urls: List<Url>,
    @field:SerializedName("thumbnail") val thumbnail: Image,
    @field:SerializedName("comics") val comics: ComicsList,
)