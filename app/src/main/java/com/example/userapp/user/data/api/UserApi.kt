package com.example.userapp.user.data.api

import com.example.userapp.user.data.api.model.UserInfoResponse
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    suspend fun fetchUsers(): List<UserInfoResponse>
}