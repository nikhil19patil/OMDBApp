package com.example.moviesdb.network

import com.example.moviesdb.model.MovieModel
import com.squareup.moshi.JsonClass

/**
 * Created by PATINIK-CONT on 04-Oct-19.
 */


@JsonClass(generateAdapter = true)
data class NetworkMoviesContainer(val Search: List<Movie>)

@JsonClass(generateAdapter = true)
data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)

fun NetworkMoviesContainer.asDomainModel(): List<MovieModel> {
    return Search.map {
        MovieModel(
            Title = it.Title,
            Year = it.Year,
            imdbID = it.imdbID,
            Type = it.Type,
            Poster = it.Poster
        )
    }
}