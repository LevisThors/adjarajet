package com.example.adjarajet

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getFavorites(): List<Movie> {
        val json = sharedPreferences.getString("favorites", "[]")
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(json, type)
    }

    fun isFavorite(movie: Movie): Boolean {
        return getFavorites().any { it.id == movie.id }
    }


    fun addFavorite(movie: Movie) {
        val favorites = getFavorites().toMutableList()
        if (!favorites.any { it.title == movie.title }) {
            favorites.add(movie)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(movie: Movie) {
        val favorites = getFavorites().toMutableList()
        favorites.removeAll { it.title == movie.title }
        saveFavorites(favorites)
    }

    fun toggleFavorite(movie: Movie): Boolean {
        return if (isFavorite(movie)) {
            removeFavorite(movie)
            false
        } else {
            addFavorite(movie)
            true
        }
    }

    private fun saveFavorites(movies: List<Movie>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(movies)
        editor.putString("favorites", json)
        editor.apply()
    }
}

