package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

data class MyRecipe (var title:String, var country:String, var resDish:String, var esFav:Long): Parcelable {
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
        parcel.writeLong(esFav)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavRecipe> {
        override fun createFromParcel(parcel: Parcel): FavRecipe {
            return FavRecipe(parcel)
        }

        override fun newArray(size: Int): Array<FavRecipe?> {
            return arrayOfNulls(size)
        }
    }
}