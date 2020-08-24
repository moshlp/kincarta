package com.example.kincarta.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var name : String? = null,
    var id  : String? = null,
    var companyName : String? = null,
    var isFavorite : Boolean? = null,
    var smallImageURL : String? = null,
    var largeImageURL : String? = null,
    var emailAddress : String? = null,
    var birthdate : String? = null,
    var phone : Phone? = null,
    var address : Address? = null
) : Parcelable