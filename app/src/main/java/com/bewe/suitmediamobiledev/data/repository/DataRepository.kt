package com.bewe.suitmediamobiledev.data.repository

import com.bewe.suitmediamobiledev.common.UiState
import com.bewe.suitmediamobiledev.data.remote.api.ApiService
import com.bewe.suitmediamobiledev.data.remote.model.UsersResponse
import retrofit2.HttpException

class DataRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun getAllUsers(currentPage: Int): UiState<UsersResponse> {
        UiState.Loading
        try {
            val response = apiService.getAllUsers(page = currentPage)
            return UiState.Success(response)
        } catch (e: HttpException) {
            return UiState.Error("HTTP Exception: ${e.message()}")
        }
    }

    companion object {
        fun getInstance(
            apiService: ApiService
        ) = DataRepository(apiService)
    }
}