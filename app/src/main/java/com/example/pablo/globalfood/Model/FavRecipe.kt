package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

data class FavRecipe (var title:String, var description:String):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
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