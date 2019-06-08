package com.example.pablo.globalfood.Model

import android.os.Parcel
import android.os.Parcelable

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

data class MyRecipe (var title:String, var country:String, var resDish:String, var esFav:Boolean): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt() == 1
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(country)
        parcel.writeString(resDish)
        parcel.writeInt(if (esFav) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyRecipe> {
        override fun createFromParcel(parcel: Parcel): MyRecipe {
            return MyRecipe(parcel)
        }

        override fun newArray(size: Int): Array<MyRecipe?> {
            return arrayOfNulls(size)
        }
    }
}