package com.example.core.di

import com.example.core.data.local.FavCharDatabase
import com.example.core.data.remote.ApiConfig
import com.example.core.data.repository.Repository
import com.example.core.domain.repository.DomRepository
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.core.domain.usecase.GetDetailUseCase
import com.example.core.domain.usecase.GetUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { FavCharDatabase.getInstance(androidContext()) }
    single { get<FavCharDatabase>().charDao() }
    single { ApiConfig.getApiService() }
    single<DomRepository> { Repository(get(), get()) }

    factory { GetUseCase(get()) }
    factory { GetDetailUseCase(get()) }
    factory { FavoriteUseCase(get()) }
}