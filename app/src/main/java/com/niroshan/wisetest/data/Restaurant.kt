package com.niroshan.wisetest.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "restaurants")
@Parcelize
data class Restaurant(
    @PrimaryKey
    val name: String,
    val type: String,
    val logo: String,
    val address: String
) : Parcelable