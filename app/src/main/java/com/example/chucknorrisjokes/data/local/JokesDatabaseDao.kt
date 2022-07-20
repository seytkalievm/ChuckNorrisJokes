package com.example.chucknorrisjokes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.chucknorrisjokes.data.local.entity.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDatabaseDao {
    @Insert
    suspend fun insert(joke: JokeEntity)

    @Query("SELECT * FROM jokes_table")
    fun getAllJokes(): Flow<List<JokeEntity>>

    @Delete
    suspend fun delete(joke: JokeEntity)
}