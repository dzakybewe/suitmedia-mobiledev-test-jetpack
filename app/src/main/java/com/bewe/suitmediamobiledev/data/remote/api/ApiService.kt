package com.bewe.suitmediamobiledev.data.remote.api

import com.bewe.suitmediamobiledev.data.remote.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getAllUsers(
        @Query("page")
        page: Int = 1,

        @Query("per_page")
        perPage: Int = 10
    ): UsersResponse
}