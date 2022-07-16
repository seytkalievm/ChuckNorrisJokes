package com.example.chucknorrisjokes.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.local.JokesDatabase
import com.example.chucknorrisjokes.data.local.JokesDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokesRepository (application: Application){
    private var jokesDao: JokesDatabaseDao
    private var coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val db: JokesDatabase = JokesDatabase.getInstance(application)
        jokesDao = db.jokesDatabaseDao
    }

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