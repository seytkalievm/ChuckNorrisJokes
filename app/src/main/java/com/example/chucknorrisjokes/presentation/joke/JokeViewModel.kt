package com.example.chucknorrisjokes.presentation.joke


import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.domain.repository.LocalJokesRepository
import com.example.chucknorrisjokes.domain.repository.RemoteJokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val localRepository: LocalJokesRepository,
    private val remoteRepository: RemoteJokesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val category = savedStateHandle.get<String>("selectedCategory")

    private val _status = MutableLiveData<Resource<Joke>>()
    val status: LiveData<Resource<Joke>> get() = _status


    private var _joke: Joke? = null

    private val _saveStatus = MutableLiveData<Int>()
    val saveStatus: LiveData<Int> get() = _saveStatus


    init {
        getJoke()
    }

    fun updateJoke(){
        getJoke()
    }

    fun addJokeToFavorites(){
        _joke?.let {joke ->
            viewModelScope.launch(Dispatchers.IO) {
                try{
                    localRepository.saveToFavorites(joke)
                    _saveStatus.postValue(R.string.save_success)

                } catch (e: SQLiteConstraintException){
                    _saveStatus.postValue(R.string.already_saved)
                } catch (e: Exception){
                    _saveStatus.postValue(R.string.save_failure)
                }
            }
        }
    }


    private fun getJoke(){
        _status.postValue(Resource.Loading())
        category?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _joke = remoteRepository.getJokeOnCategory(category)
                    _status.postValue(Resource.Success(data = _joke!!))
                }
                catch (e: Exception){
                    _status.postValue(Resource.Error(message = e.message?:"UnknownError"))
                }
            }
        }
    }



}