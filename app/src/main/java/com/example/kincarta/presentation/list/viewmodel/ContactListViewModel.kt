package com.example.kincarta.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kincarta.data.repository.repository.MainRepository
import com.example.kincarta.utils.Resource
import kotlinx.coroutines.Dispatchers

class ContactListViewModel (private val mainRepository: MainRepository)  : ViewModel() {

    fun getcontacts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getContacts()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}