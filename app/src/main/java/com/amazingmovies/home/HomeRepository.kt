package com.amazingmovies.home

import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.models.GetMoviesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeRepository @Inject constructor(val api: Api) {

    fun getMovies(sortBy: String, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = api
        .getMovies(sortBy)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

    fun getUpcomingMovies(year: String, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = api
        .getUpcomingMovies(year)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }
}