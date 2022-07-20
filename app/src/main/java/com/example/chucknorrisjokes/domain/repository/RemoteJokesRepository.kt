package com.example.chucknorrisjokes.domain.repository

import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.model.search.SearchResult

interface RemoteJokesRepository {

    suspend fun getRandomJoke(): Joke

    suspend fun getJokeOnCategory(category: String): Joke

    suspend fun getCategories(): List<Category>

    suspend fun getJokesOnQuery(query: String): SearchResult
}