package com.example.chucknorrisjokes.presentation.search

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.repository.LocalJokesRepository
import com.example.chucknorrisjokes.domain.repository.RemoteJokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val localRepository: LocalJokesRepository,
    private val remoteRepository: RemoteJokesRepository
): ViewModel() {

    private val _status = MutableLiveData<Resource<List<Joke>>>()
    val status: LiveData<Resource<List<Joke>>> get() = _status


    fun search(value :String){
        getJokes(value)
    }

    fun addJoke(joke: Joke){
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.saveToFavorites(joke)
        }
    }

    private fun getJokes(query: String){
        _status.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val result = remoteRepository.getJokesOnQuery(query)
                Log.i(TAG, "getJokes: $result")
                _status.postValue(Resource.Success(result.result))

            } catch (e: SQLiteConstraintException){
                _status.postValue(Resource.Error("Joke is already saved"))
            }
            catch (t: Throwable){
                Log.i("E", t.message.toString())
                _status.postValue(Resource.Error(t.message?:"Unknown Error"))
            }
        }
    }




}