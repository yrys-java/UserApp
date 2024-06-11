package com.example.userapp.user.domain.repository

import com.example.userapp.user.domain.model.UserInfo

interface UserRemoteRepository {

    suspend fun fetchUsers(): List<UserInfo>
}