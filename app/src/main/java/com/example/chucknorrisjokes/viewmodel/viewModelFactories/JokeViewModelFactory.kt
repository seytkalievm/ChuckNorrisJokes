package com.example.chucknorrisjokes.viewmodel.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisjokes.presentation.joke.JokeViewModel
import java.lang.IllegalArgumentException

class JokeViewModelFactory(private val category: String, private val app: Application)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JokeViewModel::class.java)){
            return JokeViewModel(category, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}