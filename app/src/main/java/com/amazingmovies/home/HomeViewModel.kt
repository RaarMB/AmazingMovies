package com.amazingmovies.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazingmovies.core.repository.models.GetMoviesResponse
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val homeOfflineRepository: HomeOfflineRepository,
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
    fun popularMovies(networkConnection: Boolean) {
        if(networkConnection){
            val popularity = "popularity.desc"
            homeRepository.getMovies(popularity, loaderRx)
                .subscribe({
                    getPopularMoviesLiveData.postValue(it)
                }, {
                    getPopularMoviesLiveData.postValue(null)
                })
        }else{
            homeOfflineRepository.getMovies("popularity", loaderRx)
                .subscribe({
                    getPopularMoviesLiveData.postValue(it)
                }, {
                    it.printStackTrace()
                    getPopularMoviesLiveData.postValue(null)
                })
        }

    }

    @SuppressLint("CheckResult")
    fun topRatedMovies(networkConnection: Boolean) {
        if(networkConnection) {
            val vote = "vote_average.desc"
            homeRepository.getMovies(vote, loaderRx)
                .subscribe({
                    getTopRatedMoviesLiveData.postValue(it)
                }, {
                    getTopRatedMoviesLiveData.postValue(null)
                })
        }else{
            homeOfflineRepository.getMovies("vote_average", loaderRx)
                .subscribe({
                    getTopRatedMoviesLiveData.postValue(it)
                }, {
                    getTopRatedMoviesLiveData.postValue(null)
                })
        }
    }

    @SuppressLint("CheckResult")
    fun upcomingMovies(networkConnection: Boolean) {
        val year = "2020"
        if(networkConnection) {

            homeRepository.getUpcomingMovies(year, loaderRx)
                .subscribe({
                    getUpcomingMoviesLiveData.postValue(it)
                }, {
                    getUpcomingMoviesLiveData.postValue(null)
                })
        }else{
            homeOfflineRepository.getMoviesUpcoming(year, loaderRx)
                .subscribe({
                    getUpcomingMoviesLiveData.postValue(it)
                }, {
                    getUpcomingMoviesLiveData.postValue(null)
                })
        }
    }

}