package com.example.chucknorrisjokes.data.local

import android.content.Context
import androidx.room.*
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Joke::class], version = 1, exportSchema = false)
@TypeConverters(CategoriesConverter::class)
abstract class JokesDatabase: RoomDatabase(){
    abstract val jokesDatabaseDao: JokesDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: JokesDatabase? = null

        fun getInstance(context: Context): JokesDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext, JokesDatabase::class.java, "jokes_table")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }


    }
}