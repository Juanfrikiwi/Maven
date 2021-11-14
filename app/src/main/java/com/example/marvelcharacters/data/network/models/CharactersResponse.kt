package com.example.marvelcharacters.data.network.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersResponse(
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("description") val description: String?= null,
    @field:SerializedName("modified") val modified: Date?= null,
    @field:SerializedName("resourceURI") val resourceURI: String?= null,
    @field:SerializedName("urls") val urls: List<UrlResponse>?= null,
    @field:SerializedName("thumbnail") val thumbnail: ImageResponse?= null,
    @field:SerializedName("comics") val comics: ComicsList?= null,
)