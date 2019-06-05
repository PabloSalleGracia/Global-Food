package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

data class Review (var idReview:Int, var title:String, var nombreAutor:String, var country:String, var resDish:String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idReview)
        parcel.writeString(title)
        parcel.writeString(nombreAutor)
        parcel.writeString(country)
        parcel.writeString(resDish)

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