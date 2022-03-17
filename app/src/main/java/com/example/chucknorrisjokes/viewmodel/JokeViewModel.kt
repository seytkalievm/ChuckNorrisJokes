package com.example.chucknorrisjokes.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chucknorrisjokes.model.network.ChuckNorrisApi
import com.example.chucknorrisjokes.model.database.Joke
import com.example.chucknorrisjokes.model.database.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class JokeViewModel(private val category: String, app: Application) : AndroidViewModel(app) {

    private val repository = JokesRepository(app)


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
            try {
                repository.addJoke(joke)
            }catch (E: Exception){
            }
        }
    }


    private fun getJoke(){
        coroutineScope.launch {
            val getJokeDeferred = ChuckNorrisApi.RETROFIT_SERVICE.getJokeOnCategoryAsync(category)
            try {
                val result =getJokeDeferred.await()
                if (result.url.isNotEmpty()){
                    _joke.value = result
                }
            }catch (t: SQLiteConstraintException){
                Log.i("E", t.message.toString())
            }
        }
    }



}