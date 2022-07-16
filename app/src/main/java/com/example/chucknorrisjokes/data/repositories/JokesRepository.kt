package com.example.chucknorrisjokes.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.local.JokesDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class JokesRepository @Inject constructor(
    private val jokesDao: JokesDatabaseDao
) {
    private var coroutineScope = CoroutineScope(Dispatchers.IO)


    fun getAllJokes(): LiveData<List<Joke>>{
        return jokesDao.getAllJokes()
    }

    fun addJoke(joke: Joke) {
        coroutineScope.launch {
            try {
                asyncAddJoke(joke)
            } catch (E: Exception) {
                Log.i("E", E.message.toString())
            }
        }
    }

    private fun asyncAddJoke(joke: Joke){
        jokesDao.insert(joke)
    }

    fun deleteJoke(joke: Joke){
        coroutineScope.launch {
            asyncDeleteJoke(joke)
        }
    }

    private fun asyncDeleteJoke(joke: Joke){
        jokesDao.delete(joke)
    }


}