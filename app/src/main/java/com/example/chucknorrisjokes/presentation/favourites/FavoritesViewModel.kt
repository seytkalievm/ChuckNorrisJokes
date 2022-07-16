package com.example.chucknorrisjokes.presentation.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.repositories.JokesRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = JokesRepository(application)

    val jokes: LiveData<List<Joke>> = repository.getAllJokes()

    fun deleteJoke(joke: Joke){
        repository.deleteJoke(joke)
    }

}