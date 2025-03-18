package com.example.core.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character")
    suspend fun getCharList() : Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharDetails(
        @Path("id") id: Int
    ) : Response<Characters>
}