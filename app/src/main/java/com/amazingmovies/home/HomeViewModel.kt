package com.amazingmovies.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazingmovies.core.repository.models.GetMoviesResponse
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val loaderRx: MutableLiveData<Boolean>
) : ViewModel() {

    private val getPopularMoviesLiveData: MutableLiveData<GetMoviesResponse> = MutableLiveData()
    private val getTopRatedMoviesLiveData: MutableLiveData<GetMoviesResponse> = MutableLiveData()
    private val getUpcomingMoviesLiveData: MutableLiveData<GetMoviesResponse> = MutableLiveData()

    fun getPopularMovies(): LiveData<GetMoviesResponse> = getPopularMoviesLiveData
    fun getTopRatedMovies(): LiveData<GetMoviesResponse> = getTopRatedMoviesLiveData
    fun getUpcomingMovies(): LiveData<GetMoviesResponse> = getUpcomingMoviesLiveData

    fun loading(): LiveData<Boolean> = loaderRx

    @SuppressLint("CheckResult")
    fun popularMovies() {
        val popularity = "popularity.desc"
        homeRepository.getMovies(popularity, loaderRx)
            .subscribe({
                getPopularMoviesLiveData.postValue(it)
            }, { error ->
                getPopularMoviesLiveData.postValue(null)
            })
    }

    @SuppressLint("CheckResult")
    fun topRatedMovies() {
        val vote = "vote_average.desc"
        homeRepository.getMovies(vote, loaderRx)
            .subscribe({
                getTopRatedMoviesLiveData.postValue(it)
            }, { error ->
                getTopRatedMoviesLiveData.postValue(null)
            })
    }

    @SuppressLint("CheckResult")
    fun upcomingMovies() {
        val year = "2020"
        homeRepository.getUpcomingMovies(year, loaderRx)
            .subscribe({
                getUpcomingMoviesLiveData.postValue(it)
            }, { error ->
                getUpcomingMoviesLiveData.postValue(null)
            })
    }

}