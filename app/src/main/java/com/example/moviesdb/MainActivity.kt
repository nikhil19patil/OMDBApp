package com.example.moviesdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviesdb.databinding.ActivityMainBinding
import com.example.moviesdb.utility.showSnackBar
import com.example.moviesdb.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: SharedViewModel
    lateinit var binding: ActivityMainBinding
    var connectedToNetwork = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
        viewModel.networkLiveData.observe(this, Observer {
            if (!it) {
                displayOfflineSnack()
            } else {
                binding.clMain.showSnackBar("Internet in back. You can resume using app now.")
            }
            connectedToNetwork = it
        })
    }

    fun displayOfflineSnack() {
        binding.clMain.showSnackBar("You are offline. Please connect to the internet")
    }

    fun setToolbarTitle(myTitle: String) {
        supportActionBar?.title = myTitle
    }

    fun setBackButtonEnabled(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}