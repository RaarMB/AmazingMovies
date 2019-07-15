package com.amazingmovies.home

import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.repository.models.GetMoviesResponse
import com.amazingmovies.core.repository.models.MovieInfo
import com.vicpin.krealmextensions.querySortedAsSingle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Sort

class HomeOfflineRepository {

    fun getMovies(field: String, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = MovieInfo()
        .querySortedAsSingle(field, Sort.DESCENDING)
        .toObservable()
        .map {
            val response = GetMoviesResponse()
            response.results = it
            return@map response
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

    fun getMoviesUpcoming(year: String, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = MovieInfo()
        .querySortedAsSingle("release_date", Sort.DESCENDING)
        .toObservable()
        .map {
            val response = GetMoviesResponse()
            response.results = it
            return@map response
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

}