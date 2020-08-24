package com.example.kincarta.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address (
    var street  : String? = null,
    var city  : String? = null,
    var state  : String? = null,
    var country  : String? = null,
    var zipCode  : String? = null
) : Parcelable