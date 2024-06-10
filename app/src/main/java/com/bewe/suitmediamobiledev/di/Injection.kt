package com.bewe.suitmediamobiledev.di

import com.bewe.suitmediamobiledev.data.remote.api.ApiConfig
import com.bewe.suitmediamobiledev.data.repository.DataRepository

object Injection {
    fun provideRepository(): DataRepository {
        val apiService = ApiConfig.getApiService()

        return DataRepository.getInstance(apiService)
    }
}