package com.example.marvelcharacters.utilities


import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import java.text.SimpleDateFormat
import java.util.*

//TODO igual que el del modulo DATA.  Ver un sitio comun para...
object Mappers {

    fun mapperToEntity(characterResponse: CharactersResponse): CharactersEntity {
        characterResponse.let {
            return CharactersEntity(
                it.id,
                it.name,
                it.description,
                DateUtils.getDateFormatted(it.modified.time),
                it.resourceURI,
                it.thumbnail.path + "." + it.thumbnail.extension,
                listOf("2", "3")
            )
        }
    }

}
