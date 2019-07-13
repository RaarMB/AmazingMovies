package com.amazingmovies.core.configuration.di.modules

import com.amazingmovies.detail.MovieDetailFragment
import com.amazingmovies.home.HomeFragment
import com.amazingmovies.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

}