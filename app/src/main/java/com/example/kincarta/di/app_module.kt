package com.example.kincarta.di

import com.example.kincarta.data.api.ApiHelper
import com.example.kincarta.data.api.RetrofitBuilder
import com.example.kincarta.data.repository.MainRepository
import com.example.kincarta.presentation.list.viewmodel.ContactListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val kcModule = module {
    viewModel { ContactListViewModel(get()) }


    single {
        MainRepository(get())
    }

    single {
        ApiHelper(get())
    }

    single {
        RetrofitBuilder.apiService
    }

}

val kcApp = listOf(kcModule)