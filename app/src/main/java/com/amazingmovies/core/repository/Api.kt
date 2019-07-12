package com.amazingmovies.core.repository

import com.amazingmovies.BuildConfig
import com.amazingmovies.core.configuration.constants.NameServices
import com.amazingmovies.core.repository.models.GetMoviesResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNTA1ZGI4ZTUwZWYwZDVmNDUyNTZjMWRmNzY2YjhiMiIsInN1YiI6IjVkMWZlMTNmMTVjNjM2NjliZGY0ZDBkNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lGq81Zt85fBwAvR3au1zM1oi9pW3AOvLH2eIjBC3cJ8")
    @GET(NameServices.MOVIE)
    fun getMovies(@Query("sort_by") sortBy: String): Observable<GetMoviesResponse>

    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNTA1ZGI4ZTUwZWYwZDVmNDUyNTZjMWRmNzY2YjhiMiIsInN1YiI6IjVkMWZlMTNmMTVjNjM2NjliZGY0ZDBkNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lGq81Zt85fBwAvR3au1zM1oi9pW3AOvLH2eIjBC3cJ8")
    @GET(NameServices.MOVIE)
    fun getUpcomingMovies(@Query("primary_release_year") year: String): Observable<GetMoviesResponse>

    companion object {
        fun create(): Api {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .build()
            return retrofit.create(Api::class.java)
        }
    }

}