package com.example.marvelcharacters.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MarvelService {

    companion object {
        private const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        const val apiKey = "?apikey=3514f3813b164a2099f7dded753edcb0"
        const val GET_CHARACTERS = "characters$apiKey"

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

    @GET(BASE_URL + GET_CHARACTERS)
    fun getListCharacters(): List<CharactersResponse>


}
