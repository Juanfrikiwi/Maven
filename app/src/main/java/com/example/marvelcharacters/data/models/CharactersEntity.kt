package com.example.marvelcharacters.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersEntity(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String?= null,
    @field:SerializedName("modified") val modified: Date?= null,
    @field:SerializedName("resourceURI") val resourceURI: String?= null,
    @field:SerializedName("urls") val urls: List<Url>?= null,
    @field:SerializedName("thumbnail") val thumbnail: Image?= null,
    @field:SerializedName("comics") val comics: ComicsList?= null,
)