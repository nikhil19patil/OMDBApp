package com.example.moviesdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.database.getDatabase
import com.example.moviesdb.model.MovieDetails
import com.example.moviesdb.network.asDomainModel
import com.example.moviesdb.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by PATINIK-CONT on 07-Oct-19.
 */
class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(getDatabase(application))

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var movieDetails = MutableLiveData<MovieDetails>()

    fun fetchMovieDetails(imdbId: String) {
        val map = HashMap<String, String>()
        map["i"] = imdbId
        map["apikey"] = "457d975b"

        viewModelScope.launch {
            movieDetails.postValue(repository.fetchMovieDetails(map).asDomainModel())
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
                return MovieDetailsViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct Viewmodel")
        }
    }
}