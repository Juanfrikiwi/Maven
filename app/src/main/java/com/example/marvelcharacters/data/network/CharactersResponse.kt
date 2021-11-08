package com.example.marvelcharacters.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class CharactersResponse(

    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("modified") val modified: Date,
    @field:SerializedName("resourceURI") val resourceURI: String,
    @field:SerializedName("urls") val urls: List<Url>,
    @field:SerializedName("thumbnail") val thumbnail: Image,
    @field:SerializedName("comics") val comics: ComicsList,
)
data class Url(
    @field:SerializedName("type") val type: String,
    @field:SerializedName("url") val url: String,
)

data class Image(
    @field:SerializedName("path") val path: String,
    @field:SerializedName("extension") val extension: String,
)

data class ComicsList(
    @field:SerializedName("available") val available: Int,
    @field:SerializedName("returned") val returned: Int,
    @field:SerializedName("collectionURI") val collectionURI: String,
    @field:SerializedName("items") val items: List<CommicSummary>,
)

data class CommicSummary(
    @field:SerializedName("resource") val resource: String,
    @field:SerializedName("name") val name: String,
)
