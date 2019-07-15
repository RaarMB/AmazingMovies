package com.amazingmovies.core.configuration.realm

import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmMigration

class RealmMigrationConfig: RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        if (oldVersion < newVersion){
            Realm.deleteRealm(Realm.getDefaultConfiguration())
        }
    }

}