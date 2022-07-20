package com.example.chucknorrisjokes.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.chucknorrisjokes.domain.model.category.Category
import com.example.chucknorrisjokes.domain.model.joke.Joke
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "jokes_table")
data class JokeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "categories")
    val categories: List<String>,
    @ColumnInfo(name = "value")
    @NonNull
    val value: String,
    ){
    override fun toString(): String {
        return "$value\n$id\n${categories}"
    }
}

fun JokeEntity.toJoke(): Joke{
    return Joke(
        categories = categories.map { Category(it) },
        id = id,
        joke = value
    )
}


class CategoriesConverter{
    @TypeConverter
    fun fromList(list: List<String>?):String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(string: String):List<String>{
        val listType = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(string, listType)
    }

}