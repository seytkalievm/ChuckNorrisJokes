package com.example.chucknorrisjokes.data.remote

import com.example.chucknorrisjokes.data.remote.dto.RandomJokeDto
import com.example.chucknorrisjokes.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi{
    @GET("random")
    suspend fun getRandomJoke(): RandomJokeDto

    @GET("categories")
    suspend fun getCategories(): List<String>

    @GET("random")
    suspend fun getJokeOnCategory(@Query("category") category: String): RandomJokeDto

    @GET("search")
    suspend fun getJokesOnQuery(@Query("query") query: String): SearchResultDto
}
