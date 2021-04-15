package com.amazingmovies.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazingmovies.core.repository.models.GetMoviesResponse
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val loaderRx: MutableLiveData<Boolean>
) : ViewModel() {

    private val getFindMoviesLiveData: MutableLiveData<GetMoviesResponse> = MutableLiveData()

    fun getFindMovies(): LiveData<GetMoviesResponse> = getFindMoviesLiveData

    fun loading(): LiveData<Boolean> = loaderRx

    @SuppressLint("CheckResult")
    fun findMovies(searchInput: String) {
        searchRepository.findMoviesByGenres(API_KEY, searchInput, loaderRx)
            .subscribe({
                getFindMoviesLiveData.postValue(it)
            }, {
                getFindMoviesLiveData.postValue(null)
            })
    }

    private companion object {
        const val API_KEY = "a505db8e50ef0d5f45256c1df766b8b2"
    }

}