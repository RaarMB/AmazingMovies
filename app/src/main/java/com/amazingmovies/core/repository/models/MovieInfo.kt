package com.amazingmovies.core.repository.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class MovieInfo(
    val popularity: Float?,
    val id: Int?,
    val video: Boolean?,
    val vote_count: Int?,
    val vote_average: Float?,
    val title: String?,
    val release_date: String?,
    val original_language: String?,
    val original_title: String?,
    val genre_ids: IntArray?,
    val backdrop_path: String?,
    val adult: Boolean?,
    val overview: String?,
    val poster_path: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createIntArray(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(popularity)
        parcel.writeValue(id)
        parcel.writeValue(video)
        parcel.writeValue(vote_count)
        parcel.writeValue(vote_average)
        parcel.writeString(title)
        parcel.writeString(release_date)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(backdrop_path)
        parcel.writeValue(adult)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieInfo

        if (popularity != other.popularity) return false
        if (id != other.id) return false
        if (video != other.video) return false
        if (vote_count != other.vote_count) return false
        if (vote_average != other.vote_average) return false
        if (title != other.title) return false
        if (release_date != other.release_date) return false
        if (original_language != other.original_language) return false
        if (original_title != other.original_title) return false
        if (genre_ids != null) {
            if (other.genre_ids == null) return false
            if (!genre_ids.contentEquals(other.genre_ids)) return false
        } else if (other.genre_ids != null) return false
        if (backdrop_path != other.backdrop_path) return false
        if (adult != other.adult) return false
        if (overview != other.overview) return false
        if (poster_path != other.poster_path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = popularity?.hashCode() ?: 0
        result = 31 * result + (id ?: 0)
        result = 31 * result + (video?.hashCode() ?: 0)
        result = 31 * result + (vote_count ?: 0)
        result = 31 * result + (vote_average?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (release_date?.hashCode() ?: 0)
        result = 31 * result + (original_language?.hashCode() ?: 0)
        result = 31 * result + (original_title?.hashCode() ?: 0)
        result = 31 * result + (genre_ids?.contentHashCode() ?: 0)
        result = 31 * result + (backdrop_path?.hashCode() ?: 0)
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (poster_path?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<MovieInfo> {
        override fun createFromParcel(parcel: Parcel): MovieInfo {
            return MovieInfo(parcel)
        }

        override fun newArray(size: Int): Array<MovieInfo?> {
            return arrayOfNulls(size)
        }
    }
}