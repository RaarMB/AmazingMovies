package com.amazingmovies.search

import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.ApiGenre
import com.amazingmovies.core.repository.models.GetMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiGenre: ApiGenre, val api: Api) {

    private fun getFindMovies(withGenres: Int, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = api
        .getCategoryMovies(withGenres)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

    fun findMoviesByGenres(apiKey: String, searchInput: String, loaderRx: MutableLiveData<Boolean>): Observable<GetMoviesResponse> = apiGenre
        .getGenre(apiKey)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map {
            return@map it.genres?.find { genre ->
                return@find genre.name?.contains(searchInput, true) ?: false
            }
        }
        .flatMap {
            return@flatMap getFindMovies(it.id ?: 0, loaderRx)
        }
        .doOnSubscribe { loaderRx.postValue(true) }
        .doOnTerminate { loaderRx.postValue(false) }

}