package com.example.moviesdb.model

import com.example.moviesdb.database.MovieSearchKey
import java.lang.NumberFormatException
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
    val Title: String?,
    val Year: String?,
    val imdbID: String?,
    val Type: String?,
    val Poster: String?

)

data class MovieDetails(
    val Title: String?,
    val Year: String?,
    val Rated: String?,
    val Released: String?,
    val Runtime: String?,
    val Genre: String?,
    val Director: String?,
    val Writer: String?,
    val Actors: String?,
    val Plot: String?,
    val Language: String?,
    val Country: String?,
    val Awards: String?,
    val Poster: String?,
    val Ratings: List<Rating>?,
    val Metascore: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val imdbID: String?,
    val Type: String?,
    val BoxOffice: String?,
    val Production: String?,
    val Website: String?,
    val Response: String?
) {
    val rating: Float
        get() {
            return try {
                imdbRating?.toFloat() ?: 0.0f
            } catch (e: NumberFormatException) {
                0.0f
            }
        }

}

data class Rating(
    val Source: String,
    val Value: String
)