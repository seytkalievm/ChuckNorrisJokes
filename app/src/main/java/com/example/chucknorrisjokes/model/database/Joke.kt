package com.example.chucknorrisjokes.model.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "jokes_table")
data class Joke(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "categories")
    val categories: List<String>,
    @ColumnInfo(name = "value")
    @NonNull
    val value: String,
    ){
    override fun toString(): String {
        return "$value\n$url\n${categories}"
    }

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