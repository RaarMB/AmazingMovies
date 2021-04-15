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
        if (networkConnection) {
            homeRepository.getMovies(POPULARITY_DESC, loaderRx)
                .subscribe({
                    getPopularMoviesLiveData.postValue(it)
                }, {
                    getPopularMoviesLiveData.postValue(null)
                })
        } else {
            homeOfflineRepository.getMovies(POPULARITY, loaderRx)
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
        if (networkConnection) {
            homeRepository.getMovies(VOTE_AVERAGE_DESC, loaderRx)
                .subscribe({
                    getTopRatedMoviesLiveData.postValue(it)
                }, {
                    getTopRatedMoviesLiveData.postValue(null)
                })
        } else {
            homeOfflineRepository.getMovies(VOTE_AVERAGE, loaderRx)
                .subscribe({
                    getTopRatedMoviesLiveData.postValue(it)
                }, {
                    getTopRatedMoviesLiveData.postValue(null)
                })
        }
    }

    @SuppressLint("CheckResult")
    fun upcomingMovies(networkConnection: Boolean) {
        if (networkConnection) {
            homeRepository.getUpcomingMovies(YEAR, loaderRx)
                .subscribe({
                    getUpcomingMoviesLiveData.postValue(it)
                }, {
                    getUpcomingMoviesLiveData.postValue(null)
                })
        } else {
            homeOfflineRepository.getMoviesUpcoming(YEAR, loaderRx)
                .subscribe({
                    getUpcomingMoviesLiveData.postValue(it)
                }, {
                    getUpcomingMoviesLiveData.postValue(null)
                })
        }
    }

    private companion object {
        const val POPULARITY_DESC = "popularity.desc"
        const val POPULARITY = "popularity"
        const val VOTE_AVERAGE_DESC = "vote_average.desc"
        const val VOTE_AVERAGE = "vote_average"
        const val YEAR = "2020"
    }
}