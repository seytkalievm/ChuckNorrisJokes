package com.example.chucknorrisjokes.data.remote.dto


import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.squareup.moshi.Json

data class RandomJokeDto(
    @Json(name = "categories")
    val categories: List<String>,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "icon_url")
    val iconUrl: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "value")
    val value: String
)

fun RandomJokeDto.toJoke():Joke{
    val categories = categories.map{Category(it)}
    return Joke(joke = value, categories = categories, id = id)
}