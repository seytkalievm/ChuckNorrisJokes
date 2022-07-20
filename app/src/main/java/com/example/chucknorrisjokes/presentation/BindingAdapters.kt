package com.example.chucknorrisjokes.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorrisjokes.R
import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.example.chucknorrisjokes.presentation.categories.CategoriesAdapter
import com.example.chucknorrisjokes.presentation.favourites.FavoriteJokesAdapter
import com.example.chucknorrisjokes.presentation.search.SearchResultsAdapter
import java.util.*


@BindingAdapter("joke")
fun TextView.bindJokeValue(joke: Joke?){
    if (joke == null) this.text = R.string.joke_example.toString() else this.text = joke.joke
}

@BindingAdapter("category")
fun TextView.bindJokeCategory(joke: Joke?){
    if (joke == null)
        this.text = R.string.category.toString()

    else this.text = joke.categories[0]
        .category
        .replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else
                it.toString()
    }
}