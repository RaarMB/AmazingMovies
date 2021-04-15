package com.amazingmovies.core.broadcasts

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.amazingmovies.core.extensions.hasConnection
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.ApiGenre
import com.amazingmovies.core.repository.models.MovieInfo
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.saveAll
import dagger.android.AndroidInjection
import javax.inject.Inject

class ConnectionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var apiGenre: ApiGenre

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
        val isConnected = context.hasConnection
        if(isConnected){
            api.getMovies().subscribe { movies ->
                MovieInfo().deleteAll()
                movies.results?.saveAll()
            }

        }else{
            MovieInfo().queryAll()
        }

    }
}