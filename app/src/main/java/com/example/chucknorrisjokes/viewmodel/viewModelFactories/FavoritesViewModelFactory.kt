package com.example.chucknorrisjokes.viewmodel.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisjokes.model.database.JokesDatabaseDao
import com.example.chucknorrisjokes.viewmodel.FavoritesViewModel
import java.lang.IllegalArgumentException
import javax.sql.DataSource

class FavoritesViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}