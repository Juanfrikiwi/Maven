package com.example.marvelcharacters.domain.models

import java.util.*

data class Characters(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val modified: Date? = null,
    val resourceURI: String? = null,
    val urls: List<Url>? = null,
    val thumbnail: Image? = null,
    val comics: ComicsList? = null,
)

data class Url(
    val type: String? = null,
    val url:String? = null,
)

data class Image(
    val path: String? = null,
    val extension :String? = null,
)

data class ComicsList(
    val available: Int? = null,
    val returned: Int? = null,
    val collectionURI:String? = null,
    val items: List<CommicSummary>
)

data class CommicSummary(
    val resource: String? = null,
    val name :String? = null,
)