package com.amazingmovies.core.application

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import androidx.fragment.app.Fragment
import com.amazingmovies.core.configuration.di.DaggerAppComponent
import com.amazingmovies.core.configuration.di.modules.ContextModule
import com.amazingmovies.core.configuration.realm.RealmMigrationConfig
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class CoreApplication: Application(), HasActivityInjector, HasSupportFragmentInjector, HasBroadcastReceiverInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()
            .inject(this)
        Realm.init(this)
        Realm.deleteRealm(Realm.getDefaultConfiguration())
        val realmConfiguration = RealmConfiguration.Builder()
            .name("AmazingMovies.Realm")
            .schemaVersion(1)
            .migration(RealmMigrationConfig())
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = broadcastReceiverInjector
}