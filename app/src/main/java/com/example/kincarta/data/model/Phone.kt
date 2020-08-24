package com.example.kincarta.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Phone (
    var work : String? = null,
    var home : String? = null,
    var mobile : String? = null
) : Parcelable