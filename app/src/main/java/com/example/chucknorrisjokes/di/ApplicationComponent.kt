package com.example.chucknorrisjokes.di

import android.content.Context
import com.example.chucknorrisjokes.data.local.JokesDatabase
import com.example.chucknorrisjokes.data.local.JokesDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationComponent {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JokesDatabase{
        return JokesDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideJokesDao(db: JokesDatabase): JokesDatabaseDao{
        return db.jokesDatabaseDao
    }


}