package com.example.moviesdb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moviesdb.database.MovieDatabase
import com.example.moviesdb.database.asDomainModel
import com.example.moviesdb.model.SearchKeyModel
import com.example.moviesdb.network.NetworkMoviesContainer
import com.example.moviesdb.network.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
class MovieRepository(private val database: MovieDatabase) {

    val searchKeys: LiveData<List<SearchKeyModel>> = Transformations.map(
        database.movieSearchKeyDao.getAllSearchKeys()
    ) {
        it.asDomainModel()
    }

    suspend fun insertKey(searchKeyModel: SearchKeyModel) {
        withContext(Dispatchers.IO) {
            database.movieSearchKeyDao.insertKey(searchKeyModel.asDatabaseModel())
        }
    }

    suspend fun fetchMovies(queryMap: HashMap<String, String>): NetworkMoviesContainer {
        return RetrofitObject.service.getMoviesListAsync(queryMap).await()
    }
}