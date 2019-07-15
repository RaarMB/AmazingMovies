package com.amazingmovies.core.configuration.di.modules

import com.amazingmovies.core.broadcasts.ConnectionReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BroadcastReceiverModule {

    @ContributesAndroidInjector
    fun contributeConnectionReceiver(): ConnectionReceiver
}