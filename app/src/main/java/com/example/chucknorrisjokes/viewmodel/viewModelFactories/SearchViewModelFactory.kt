package com.example.chucknorrisjokes.viewmodel.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisjokes.viewmodel.SearchViewModel
import java.lang.IllegalArgumentException

class SearchViewModelFactory(private val app: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}