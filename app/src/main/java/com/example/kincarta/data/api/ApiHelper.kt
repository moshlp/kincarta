package com.example.kincarta.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getContacts() = apiService.getContacts()
}