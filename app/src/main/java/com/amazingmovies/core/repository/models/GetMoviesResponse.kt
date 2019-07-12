package com.amazingmovies.core.repository.models

import android.os.Parcel
import android.os.Parcelable

data class GetMoviesResponse(
    val page: Int?,
    val total_results: Int?,
    val total_pages: Int?,
    val results: List<MovieInfo>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(MovieInfo)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(page)
        parcel.writeValue(total_results)
        parcel.writeValue(total_pages)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetMoviesResponse> {
        override fun createFromParcel(parcel: Parcel): GetMoviesResponse {
            return GetMoviesResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetMoviesResponse?> {
            return arrayOfNulls(size)
        }
    }
}