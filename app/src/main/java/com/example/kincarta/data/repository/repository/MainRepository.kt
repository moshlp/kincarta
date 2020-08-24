package com.example.kincarta.data.repository

import com.example.kincarta.data.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getContacts() = apiHelper.getContacts()
}


