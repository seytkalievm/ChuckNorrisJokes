package com.example.chucknorrisjokes.presentation.favourites


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.repositories.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: JokesRepository
) : ViewModel() {


    val jokes: LiveData<List<Joke>> = repository.getAllJokes()

    fun deleteJoke(joke: Joke){
        repository.deleteJoke(joke)
    }

}