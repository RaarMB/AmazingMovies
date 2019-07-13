package com.amazingmovies.core.configuration.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amazingmovies.core.configuration.architecture.ViewModelFactory
import com.amazingmovies.core.configuration.architecture.ViewModelKey
import com.amazingmovies.home.HomeViewModel
import com.amazingmovies.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun searchViewModel(viewModel: SearchViewModel): ViewModel

}