package com.example.moviesdb.model

import com.example.moviesdb.database.MovieSearchKey
import java.util.*

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */

data class SearchKeyModel(
    val id: Int?,
    val key: String,
    val date: Date
) {

    constructor(key: String, date: Date) : this(null, key, date)

    fun asDatabaseModel(): MovieSearchKey {
        return MovieSearchKey(key, date)
    }
}

data class MovieModel(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String

)

data class MovieDetails(
    val title: String,
    val year: Int,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val ratings: List<String>,
    val metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val type: String,
    val dVD: String,
    val boxOffice: String,
    val production: String,
    val website: String,
    val response: Boolean
)