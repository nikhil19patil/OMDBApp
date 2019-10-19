package com.example.moviesdb.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Created by PATINIK-CONT on 06-Oct-19.
 */

private fun Context.hasNetwork(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

fun View.showSnackBar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}