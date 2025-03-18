package com.example.rnmmobile.di

import com.example.rnmmobile.presentation.detail.DetailActivityViewModel
import com.example.rnmmobile.presentation.main.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel(get())}
    viewModel { DetailActivityViewModel(get(), get()) }
}