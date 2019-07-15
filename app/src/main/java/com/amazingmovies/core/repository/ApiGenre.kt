package com.amazingmovies.core.repository

import com.amazingmovies.BuildConfig
import com.amazingmovies.core.configuration.constants.Language
import com.amazingmovies.core.configuration.constants.NameServices
import com.amazingmovies.core.repository.models.GenreResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGenre {

    @GET(NameServices.LIST)
    fun getGenre(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String = Language.ES): Observable<GenreResponse>

    companion object {
        fun create(): ApiGenre {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.SERVER_GENRE)
                .build()
            return retrofit.create(ApiGenre::class.java)
        }
    }

}