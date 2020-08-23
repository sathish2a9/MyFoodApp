package com.winterbitegames.myfoodapp.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["foodName"])
@Parcelize
data class Store(
    var foodName: String = "",
    var foodDescription: String = "",
    var price:Int = 0,
    var count: Int = 0
) : Parcelable{

}