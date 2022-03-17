package com.example.chucknorrisjokes.model.data

import com.example.chucknorrisjokes.model.database.Joke

data class SearchResult (
    val total: Int,
    val result: List<Joke> = listOf()){
    override fun toString(): String {
        return total.toString()
    }
}