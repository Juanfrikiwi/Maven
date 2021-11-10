package com.example.marvelcharacters.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


interface MarvelService {

    @GET(BASE_URL+CHARACTERS_URL)
    suspend fun getListCharacters(
        @Query("") query: String
        ): CharacterResponse

    companion object {
        const val CHARACTERS_URL = "characters"
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        fun create(): MarvelService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelService::class.java)
        }
    }

}
