package com.example.chucknorrisjokes.model.network

import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.remote.SearchResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.chucknorris.io/jokes/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ChuckNorrisApiService{
    @GET("random")
    fun getRandomJokeAsync(): Deferred<Joke>

    @GET("categories")
    fun getCategoriesAsync(): Deferred<List<String>>

    @GET("random")
    fun getJokeOnCategoryAsync(@Query("category") category: String): Deferred<Joke>

    @GET("search")
    fun getJokesOnQueryAsync(@Query("query") query: String): Deferred<SearchResult>
}

object ChuckNorrisApi{
    val RETROFIT_SERVICE: ChuckNorrisApiService by lazy {
        retrofit.create(ChuckNorrisApiService::class.java)
    }
}