package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

data class Review (var nombreAutor:String, var descripBreve:String, var country:String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombreAutor)
        parcel.writeString(descripBreve)
        parcel.writeString(country)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }
}