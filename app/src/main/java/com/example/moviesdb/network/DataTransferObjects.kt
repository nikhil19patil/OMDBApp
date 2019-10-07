package com.example.moviesdb.network

import com.example.moviesdb.model.MovieDetails
import com.example.moviesdb.model.MovieModel
import com.example.moviesdb.model.Rating
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

@JsonClass(generateAdapter = true)
data class NetworkMovieDetail(
    val Title: String,
    val Year: Int,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Rating>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
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

fun NetworkMovieDetail.asDomainModel(): MovieDetails {
    return MovieDetails(
        Title = this.Title,
        Year = this.Year,
        Rated = this.Rated,
        Released = this.Released,
        Runtime = this.Runtime,
        Genre = this.Genre,
        Director = this.Director,
        Writer = this.Writer,
        Actors = this.Actors,
        Plot = this.Plot,
        Language = this.Language,
        Country = this.Country,
        Awards = this.Awards,
        Poster = this.Poster,
        Ratings = this.Ratings,
        Metascore = this.Metascore,
        imdbRating = this.imdbRating,
        imdbVotes = this.imdbVotes,
        imdbID = this.imdbID,
        Type = this.Type,
        DVD = this.DVD,
        BoxOffice = this.BoxOffice,
        Production = this.Production,
        Website = this.Website,
        Response = this.Response
    )
}