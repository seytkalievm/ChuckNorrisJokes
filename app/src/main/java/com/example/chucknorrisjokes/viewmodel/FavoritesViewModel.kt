package com.example.chucknorrisjokes.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.chucknorrisjokes.model.database.Joke
import com.example.chucknorrisjokes.model.database.JokesDatabaseDao
import com.example.chucknorrisjokes.model.database.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = JokesRepository(application)

    val jokes: LiveData<List<Joke>> = repository.getAllJokes()

    init {
        Log.i("PIDORAAAAAAS", jokes.value.toString())
    }


    override fun onCleared() {
        super.onCleared()
    }

    fun deleteJoke(joke: Joke){
        repository.deleteJoke(joke)
    }

}