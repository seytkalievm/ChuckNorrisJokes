package com.example.chucknorrisjokes.data.repositories

import android.util.Log
import com.example.chucknorrisjokes.data.remote.ChuckNorrisApi
import com.example.chucknorrisjokes.data.remote.dto.toJoke
import com.example.chucknorrisjokes.data.remote.dto.toSearchResult
import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.model.search.SearchResult
import com.example.chucknorrisjokes.domain.repository.RemoteJokesRepository
import javax.inject.Inject

private const val TAG = "RemoteJokesRepo"

class RemoteJokesRepositoryImpl @Inject constructor(
    private val api: ChuckNorrisApi
): RemoteJokesRepository {
    override suspend fun getRandomJoke(): Joke {
        return api.getRandomJoke().toJoke()
    }

    override suspend fun getJokeOnCategory(category: String): Joke { 
        try{
            return api.getJokeOnCategory(category).toJoke()
        } catch (e: Exception){
            Log.i(TAG, "getJokeOnCategory: ${e.message}")
            throw e
        }
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories().map { Category(it) }
    }

    override suspend fun getJokesOnQuery(query: String): SearchResult {
        return api.getJokesOnQuery(query).toSearchResult()
    }
}