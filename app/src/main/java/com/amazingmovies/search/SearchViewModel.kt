package com.amazingmovies.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazingmovies.core.repository.models.GenreResponse
import com.amazingmovies.core.repository.models.GetMoviesResponse
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val loaderRx: MutableLiveData<Boolean>
) : ViewModel() {

    private val getFindMoviesLiveData: MutableLiveData<GetMoviesResponse> = MutableLiveData()
    private val getGenresLiveData: MutableLiveData<GenreResponse> = MutableLiveData()

    fun getFindMovies(): LiveData<GetMoviesResponse> = getFindMoviesLiveData
    fun getGenresMovies(): LiveData<GenreResponse> = getGenresLiveData

    fun loading(): LiveData<Boolean> = loaderRx

    @SuppressLint("CheckResult")
    fun findMovies(genre: Int) {
        searchRepository.getFindMovies(genre, loaderRx)
            .subscribe({
                getFindMoviesLiveData.postValue(it)
            }, { error ->
                getFindMoviesLiveData.postValue(null)
            })
    }

    @SuppressLint("CheckResult")
    fun genres() {
        val apiKey = "a505db8e50ef0d5f45256c1df766b8b2"
        searchRepository.getGenres(apiKey, loaderRx)
            .subscribe({
                getGenresLiveData.postValue(it)
            }, { error ->
                getGenresLiveData.postValue(null)
            })
    }

}