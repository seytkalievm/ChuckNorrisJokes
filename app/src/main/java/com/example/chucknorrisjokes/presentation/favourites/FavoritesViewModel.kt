package com.example.chucknorrisjokes.presentation.favourites


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.chucknorrisjokes.data.repositories.LocalJokesRepositoryImpl
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.repository.LocalJokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: LocalJokesRepository
) : ViewModel() {

    val jokes: LiveData<List<Joke>> = repository.getAllJokes().asLiveData()

    fun deleteJoke(joke: Joke): Unit{
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFromFavourites(joke)
        }
    }

}