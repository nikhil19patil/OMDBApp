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
import com.example.moviesdb.utility.NetworkConnectionLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by PATINIK-CONT on 03-Oct-19.
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var networkLiveData = NetworkConnectionLiveData(application.applicationContext)

}