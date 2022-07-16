package com.example.chucknorrisjokes.viewmodel

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.presentation.categories.CategoriesAdapter
import com.example.chucknorrisjokes.data.local.Joke
import com.example.chucknorrisjokes.presentation.favourites.FavoriteJokesAdapter
import com.example.chucknorrisjokes.presentation.search.SearchResultsAdapter
import java.util.*


@BindingAdapter("categoriesList")
fun RecyclerView.bindRecyclerView(data: List<String>?){
    val adapter = this.adapter as CategoriesAdapter
    adapter.submitList(data)
}

@BindingAdapter("joke")
fun TextView.bindJokeValue(joke: Joke?){
    if (joke == null) this.text = R.string.joke_example.toString() else this.text = joke.value
}

@BindingAdapter("category")
fun TextView.bindJokeCategory(joke: Joke?){
    if (joke == null) this.text = R.string.category.toString() else this.text = joke.categories[0]
        .replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}

@BindingAdapter("searchResultList")
fun RecyclerView.bindSearchResultList(result: List<Joke>?){
    val adapter = this.adapter as SearchResultsAdapter
    adapter.submitList(result)
}

@BindingAdapter("favoriteJokesList")
fun RecyclerView.bindFavoriteJokesList(result: List<Joke>?){
    val adapter = this.adapter as FavoriteJokesAdapter
    adapter.submitList(result)
}