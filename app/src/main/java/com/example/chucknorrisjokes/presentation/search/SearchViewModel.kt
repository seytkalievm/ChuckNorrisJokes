package com.example.chucknorrisjokes.presentation.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chucknorrisjokes.model.network.ChuckNorrisApi
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.data.repositories.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel (application: Application): AndroidViewModel(application) {

    private val repository = JokesRepository(application)

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> get() = _total

    private val _searchResult = MutableLiveData<List<Joke>>()
    val searchResult: LiveData<List<Joke>> get() = _searchResult

    fun search(value :String){
        getJokes(value)
    }

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob+Dispatchers.Main)

    fun addJoke(joke: Joke){
        coroutineScope.launch {
            repository.addJoke(joke)
        }
    }

    private fun getJokes(query: String){
        coroutineScope.launch {
            val getJokesDeferred = ChuckNorrisApi.RETROFIT_SERVICE.getJokesOnQueryAsync(query)
            try{
                val temp = getJokesDeferred.await()
                if (temp.result.isNotEmpty()) {
                    _total.value = temp.total
                    _searchResult.value = temp.result
                    Log.i("MEME", "")
                }else{
                    _total.value = 0
                }
            }catch (t: Throwable){
                Log.i("E", t.message.toString())

            }
        }
    }




}