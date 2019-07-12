package com.amazingmovies.core.configuration.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {
    @Provides
    fun contextProvider(): Context = context
}