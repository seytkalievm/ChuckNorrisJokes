package com.example.chucknorrisjokes.presentation.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chucknorrisjokes.common.Resource
import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.repository.RemoteJokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repo: RemoteJokesRepository
): ViewModel() {

    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() =_categories

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getCategories()
    }

    private fun getCategories(){
        _categories.postValue(Resource.Loading())
        coroutineScope.launch {
            try {
                _categories.postValue(Resource.Success(repo.getCategories()))
            }catch (t: Throwable){
                _categories.postValue(Resource.Error(t.message?:"Unknown Error"))
                Log.i("E", t.toString())
            }

        }
    }


}