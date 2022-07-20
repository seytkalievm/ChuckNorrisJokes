package com.example.chucknorrisjokes.domain.model.joke

import android.app.AlertDialog
import android.content.Context
import com.example.chucknorrisjokes.data.local.entity.JokeEntity
import com.example.chucknorrisjokes.domain.model.category.Category
import java.util.*

data class Joke (
    val categories: List<Category>,
    val joke: String,
    val id: String,
)

fun Joke.toEntity(): JokeEntity{
    return JokeEntity(
        id = id,
        categories = categories.map { it.category },
        value = joke
    )
}

fun Joke.display(
    context: Context,
    positiveButtonClickListener:(Joke) -> Unit,
    positiveButtonTitle: Int,
){
    val alertDialog = AlertDialog.Builder(context)

    if (categories.isEmpty()) alertDialog.setTitle("No category") else
        alertDialog.setTitle(this
            .categories[0]
            .category
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            }
        )
    alertDialog.setMessage(this.joke)
    alertDialog.setPositiveButton(context.getText(positiveButtonTitle)) { _,_->
        positiveButtonClickListener(this)
    }
    alertDialog.setNegativeButton("Back") { _, _ -> }
    alertDialog.show()
}
