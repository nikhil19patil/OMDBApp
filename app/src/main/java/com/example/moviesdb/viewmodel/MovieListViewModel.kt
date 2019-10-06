package com.example.moviesdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.database.getDatabase
import com.example.moviesdb.model.MovieModel
import com.example.moviesdb.network.asDomainModel
import com.example.moviesdb.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val movieRepository = MovieRepository(getDatabase(application))

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var movieList = MutableLiveData<List<MovieModel>>()
    var showProgressBar = MutableLiveData<Boolean>().apply { postValue(true) }
    var showEmptyListMsg = MutableLiveData<Boolean>()

    fun fetchMovies(key: String) {
        val map = HashMap<String, String>()
        map["s"] = key
        map["apikey"] = "457d975b"

        viewModelScope.launch {
            movieList.value = movieRepository.fetchMovies(map).asDomainModel()
        }
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
                return MovieListViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct Viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}