package com.example.chucknorrisjokes.data.remote

import com.example.chucknorrisjokes.data.local.Joke

data class SearchResult (
    val total: Int,
    val result: List<Joke> = listOf()){
    override fun toString(): String {
        return total.toString()
    }
}