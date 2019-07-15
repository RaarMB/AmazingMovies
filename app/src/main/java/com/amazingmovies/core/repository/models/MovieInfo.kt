package com.amazingmovies.core.repository.models

import android.os.Parcel
import android.os.Parcelable
import com.amazingmovies.core.extensions.createRealmIntList
import com.amazingmovies.core.extensions.readRealmList
import com.amazingmovies.core.extensions.writeRealmIntList
import com.amazingmovies.core.extensions.writeRealmList
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.ArrayList

@RealmClass
open class MovieInfo(
    var popularity: Float?,
    var id: Int?,
    var video: Boolean?,
    var vote_count: Int?,
    var vote_average: Float?,
    var title: String?,
    var release_date: String?,
    var original_language: String?,
    var original_title: String?,
    var genre_ids: RealmList<Int>?,
    var backdrop_path: String?,
    var adult: Boolean?,
    var overview: String?,
    var poster_path: String?
) : RealmObject(), Parcelable {
    constructor() : this(
        -1.0f,
        -1,
        false,
        -1,
        -1.0f,
        "",
        "",
        "",
        "",
        RealmList<Int>(),
        "",
        false,
        "",
        ""
    ) {
    }

    constructor(source: Parcel) : this(
        source.readValue(Float::class.java.classLoader) as Float?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Float::class.java.classLoader) as Float?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createRealmIntList(),
        source.readString(),
        source.readValue(Boolean::class.java.classLoader) as Boolean?,
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(popularity)
        writeValue(id)
        writeValue(video)
        writeValue(vote_count)
        writeValue(vote_average)
        writeString(title)
        writeString(release_date)
        writeString(original_language)
        writeString(original_title)
        writeRealmIntList(genre_ids!!)
        writeString(backdrop_path)
        writeValue(adult)
        writeString(overview)
        writeString(poster_path)
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
        if (genre_ids != other.genre_ids) return false
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
        result = 31 * result + (genre_ids?.hashCode() ?: 0)
        result = 31 * result + (backdrop_path?.hashCode() ?: 0)
        result = 31 * result + (adult?.hashCode() ?: 0)
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (poster_path?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "MovieInfo(popularity=$popularity, id=$id, video=$video, vote_count=$vote_count, vote_average=$vote_average, title=$title, release_date=$release_date, original_language=$original_language, original_title=$original_title, genre_ids=$genre_ids, backdrop_path=$backdrop_path, adult=$adult, overview=$overview, poster_path=$poster_path)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieInfo> = object : Parcelable.Creator<MovieInfo> {
            override fun createFromParcel(source: Parcel): MovieInfo = MovieInfo(source)
            override fun newArray(size: Int): Array<MovieInfo?> = arrayOfNulls(size)
        }
    }

}