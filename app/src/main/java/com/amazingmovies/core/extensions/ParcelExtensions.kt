package com.amazingmovies.core.extensions

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmModel

fun <T> Parcel.readRealmList(clazz: Class<T>): RealmList<T>?
        where T : RealmModel,
              T : Parcelable = when {
    readInt() > 0 -> RealmList<T>().also { list ->
        repeat(readInt()) {
            list.add(readParcelable(clazz.classLoader))
        }
    }
    else -> null
}

fun <T> Parcel.writeRealmList(realmList: RealmList<T>?)
        where T : RealmModel,
              T : Parcelable {
    writeInt(when {
        realmList == null -> 0
        else -> 1
    })
    if (realmList != null) {
        writeInt(realmList.size)
        for (t in realmList) {
            writeParcelable(t, 0)
        }
    }
}

fun Parcel.createRealmIntList(): RealmList<Int>? {
    var N = readInt()
    if (N < 0) {
        return null
    }
    val l = RealmList<Int>()
    while (N > 0) {
        l.add(readInt())
        N--
    }
    return l
}

fun Parcel.writeRealmIntList(ints: RealmList<Int>) {

    if (ints != null) {
        writeInt(ints.size)
        for (t in ints) {
            writeInt(t)
        }
    }
}