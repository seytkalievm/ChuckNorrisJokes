package com.example.chucknorrisjokes.data.repositories

import com.example.chucknorrisjokes.data.local.JokesDatabaseDao
import com.example.chucknorrisjokes.data.local.entity.toJoke
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.model.joke.toEntity
import com.example.chucknorrisjokes.domain.repository.LocalJokesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LocalJokesRepositoryImpl @Inject constructor(
    private val jokesDao: JokesDatabaseDao
) : LocalJokesRepository {


    override fun getAllJokes(): Flow<List<Joke>> {
        return jokesDao.getAllJokes()
            .map {list -> list.map { 
                    jokeEntity -> jokeEntity.toJoke()
            }
        }
    }

    override suspend fun saveToFavorites(joke: Joke){
        jokesDao.insert(joke.toEntity())
    }


    override suspend fun deleteFromFavourites(joke: Joke){
        jokesDao.delete(joke.toEntity())
    }


}