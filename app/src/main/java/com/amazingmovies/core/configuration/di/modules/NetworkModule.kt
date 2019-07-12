package com.amazingmovies.core.configuration.di.modules

import com.amazingmovies.core.repository.Api
import com.amazingmovies.home.HomeRepository
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
    fun splashRepository(api: Api): HomeRepository =
        HomeRepository(api)

}