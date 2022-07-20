package com.example.chucknorrisjokes.domain.model.search

import com.example.chucknorrisjokes.domain.model.joke.Joke

data class SearchResult(
    val total: Int,
    val result: List<Joke>
)