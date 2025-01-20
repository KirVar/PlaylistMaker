package com.example.playlistmaker

import android.os.Parcel
import android.os.Parcelable

data class Track(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(trackName)
        dest.writeString(artistName)
        dest.writeLong(trackTimeMillis)
        dest.writeString(artworkUrl100)
        dest.writeString(collectionName)
        dest.writeString(releaseDate)
        dest.writeString(primaryGenreName)
        dest.writeString(country)

    }

    constructor(parcel: Parcel) : this(
        trackName = parcel.readString().toString(),
        artistName = parcel.readString().toString(),
        trackTimeMillis = parcel.readLong(),
        artworkUrl100 = parcel.readString().toString(),
        collectionName = parcel.readString().toString(),
        releaseDate = parcel.readString().toString(),
        primaryGenreName = parcel.readString().toString(),
        country = parcel.readString().toString(),
    )

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(source: Parcel): Track {
            return Track(source)
        }
        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }

}
