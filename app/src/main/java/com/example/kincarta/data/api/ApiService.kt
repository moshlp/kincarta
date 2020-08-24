package com.example.kincarta.data.api


import com.example.kincarta.data.model.Contact
import retrofit2.http.GET

interface ApiService {

    @GET("contacts.json")
    suspend fun getContacts(): List<Contact>
}