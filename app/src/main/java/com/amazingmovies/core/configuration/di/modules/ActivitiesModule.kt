package com.amazingmovies.core.configuration.di.modules

import com.amazingmovies.core.view.MainActivity
import com.amazingmovies.dashboard.DashboardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributeDashboardActivity(): DashboardActivity

}