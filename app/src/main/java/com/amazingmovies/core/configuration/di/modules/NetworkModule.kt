package com.amazingmovies.core.configuration.di.modules

import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.ApiGenre
import com.amazingmovies.home.HomeRepository
import com.amazingmovies.search.SearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun api(): Api = Api.create()

    @Provides
    @Singleton
    fun apiGenre(): ApiGenre = ApiGenre.create()

    @Provides
    @Singleton
    fun homeRepository(api: Api): HomeRepository =
        HomeRepository(api)

    @Provides
    @Singleton
    fun searchRepository(apiGenre: ApiGenre, api: Api): SearchRepository =
        SearchRepository(apiGenre, api)

}