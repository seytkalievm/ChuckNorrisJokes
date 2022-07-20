package com.example.chucknorrisjokes.data.remote.dto


import com.example.chucknorrisjokes.domain.model.search.SearchResult
import com.squareup.moshi.Json

data class SearchResultDto(
    @Json(name = "result")
    val result: List<RandomJokeDto>,
    @Json(name = "total")
    val total: Int
)

fun SearchResultDto.toSearchResult(): SearchResult{
    val jokes = this.result.map { it.toJoke() }
    return SearchResult(
        total = this.total,
        result = jokes
    )
}