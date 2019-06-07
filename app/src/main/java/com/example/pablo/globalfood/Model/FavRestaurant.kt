package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")


data class FavRestaurant (var title:String, var country:String, var resDish:String, var numFavs:Long):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(country)
        parcel.writeString(resDish)
        parcel.writeLong(numFavs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavRestaurant> {
        override fun createFromParcel(parcel: Parcel): FavRestaurant {
            return FavRestaurant(parcel)
        }

        override fun newArray(size: Int): Array<FavRestaurant?> {
            return arrayOfNulls(size)
        }
    }
}