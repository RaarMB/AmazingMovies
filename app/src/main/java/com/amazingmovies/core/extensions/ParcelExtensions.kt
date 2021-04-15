package com.amazingmovies.core.extensions

import android.os.Parcel
import io.realm.RealmList

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

    writeInt(ints.size)
    for (t in ints) {
        writeInt(t)
    }
}