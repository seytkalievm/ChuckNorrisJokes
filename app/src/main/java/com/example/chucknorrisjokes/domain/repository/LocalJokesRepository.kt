package com.example.chucknorrisjokes.domain.repository

import androidx.lifecycle.LiveData
import com.example.chucknorrisjokes.domain.model.joke.Joke
import kotlinx.coroutines.flow.Flow

interface LocalJokesRepository {

    fun getAllJokes(): Flow<List<Joke>>

    suspend fun saveToFavorites(joke: Joke)

    suspend fun deleteFromFavourites(joke: Joke)
}