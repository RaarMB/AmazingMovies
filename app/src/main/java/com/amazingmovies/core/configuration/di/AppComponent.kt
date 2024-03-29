package com.amazingmovies.core.configuration.di

import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.application.CoreApplication
import com.amazingmovies.core.configuration.di.modules.*
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.ApiGenre
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ FeaturesModule::class , NetworkModule::class, AndroidInjectionModule::class, ActivitiesModule::class, FragmentModule::class, BroadcastReceiverModule::class, ViewModelModule::class, ContextModule::class ])
interface AppComponent: AndroidInjector<CoreApplication> {
    fun api(): Api
    fun apiGenre(): ApiGenre
    fun loaderRx(): MutableLiveData<Boolean>
    fun errorMessageRx(): MutableLiveData<Int>
}