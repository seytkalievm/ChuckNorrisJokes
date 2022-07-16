package com.example.chucknorrisjokes.presentation.joke

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.example.chucknorrisjokes.model.network.ChuckNorrisApi
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.repositories.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val category = savedStateHandle.get<String>("selectedCategory")
    private val _joke = MutableLiveData<Joke>()
    val joke: LiveData<Joke> get() = _joke

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getJoke()
    }

    fun updateJoke(){
        getJoke()
    }

    fun addJokeToFavorites(joke: Joke){
        coroutineScope.launch {
            repository.addJoke(joke)
        }
    }


    private fun getJoke(){
        category?.let {
            coroutineScope.launch {
                val getJokeDeferred =
                    ChuckNorrisApi.RETROFIT_SERVICE.getJokeOnCategoryAsync(category)
                try {
                    val result = getJokeDeferred.await()
                    if (result.url.isNotEmpty()) {
                        _joke.value = result
                    }
                } catch (t: SQLiteConstraintException) {
                    Log.i("E", t.message.toString())
                }
            }
        }
    }



}