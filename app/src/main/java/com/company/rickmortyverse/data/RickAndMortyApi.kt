package com.company.rickmortyverse.data

import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>
}
