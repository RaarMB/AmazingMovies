package com.amazingmovies.core.extensions

import android.content.Context
import android.net.ConnectivityManager

val Context.hasConection: Boolean
get() {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}