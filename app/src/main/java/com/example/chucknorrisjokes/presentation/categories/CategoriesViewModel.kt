package com.example.chucknorrisjokes.presentation.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chucknorrisjokes.model.network.ChuckNorrisApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(): ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() =_categories

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedCategory = MutableLiveData<String>()
    val navigateToSelectedCategory: LiveData<String> get() = _navigateToSelectedCategory

    init{
        getCategories()
    }

    private fun getCategories(){
        coroutineScope.launch {
            val getCategoriesDeferred = ChuckNorrisApi.RETROFIT_SERVICE.getCategoriesAsync()
            try {
                val listResult = getCategoriesDeferred.await()
                if(listResult.isNotEmpty()){
                    _categories.value = listResult
                }
            }catch (t: Throwable){

                Log.i("E", t.toString())
            }

        }
    }

    fun displayJoke(category: String){
        _navigateToSelectedCategory.value = category
    }

    fun displayJokeComplete(){
        _navigateToSelectedCategory.value = "null"
    }


}