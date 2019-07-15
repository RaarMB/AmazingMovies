package com.amazingmovies.core.repository.models

import android.os.Parcel
import android.os.Parcelable
import com.amazingmovies.core.extensions.readRealmList
import com.amazingmovies.core.extensions.writeRealmList
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass

open class GetMoviesResponse(
    var page: Int?,
    var total_results: Int?,
    var total_pages: Int?,
    var results: List<MovieInfo>?
) : Parcelable {
    constructor() : this(null, -1, -1, null)
    constructor(test: Boolean): this(1,1,1, listOf(MovieInfo(true)))

    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.createTypedArrayList(MovieInfo.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(page)
        writeValue(total_results)
        writeValue(total_pages)
        writeTypedList(results)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GetMoviesResponse> = object : Parcelable.Creator<GetMoviesResponse> {
            override fun createFromParcel(source: Parcel): GetMoviesResponse = GetMoviesResponse(source)
            override fun newArray(size: Int): Array<GetMoviesResponse?> = arrayOfNulls(size)
        }
    }

}