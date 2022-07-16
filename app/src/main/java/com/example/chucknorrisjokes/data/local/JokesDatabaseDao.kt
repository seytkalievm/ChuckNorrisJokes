package com.example.chucknorrisjokes.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JokesDatabaseDao {
    @Insert
    fun insert(joke: Joke)

    @Query("SELECT * FROM jokes_table")
    fun getAllJokes():LiveData<List<Joke>>

    @Delete
    fun delete(joke: Joke)

    suspend fun asyncInsert(joke: Joke){
        insert(joke)
    }

    suspend fun asyncGetAllJokes():LiveData<List<Joke>>{
        return getAllJokes()
    }

    suspend fun asyncDelete(joke: Joke){
        delete(joke)
    }
}