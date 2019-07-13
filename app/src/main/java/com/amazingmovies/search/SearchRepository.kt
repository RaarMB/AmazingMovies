package com.amazingmovies.search

import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.ApiGenre
import com.amazingmovies.core.repository.models.GenreResponse
import com.amazingmovies.core.repository.models.GetMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(val apiGenre: ApiGenre, val api: Api) {

    fun getFindMovies(withGenres: Int, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = api
        .getCategoryMovies(withGenres)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

    fun getGenres(apiKey: String, loaderRx: MutableLiveData<Boolean>): Observable<GenreResponse> = apiGenre
        .getGenre(apiKey)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

}