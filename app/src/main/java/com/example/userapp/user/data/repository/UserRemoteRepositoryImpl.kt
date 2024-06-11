package com.example.userapp.user.data.repository

import com.example.userapp.user.data.api.UserApi
import com.example.userapp.user.domain.model.UserInfo
import com.example.userapp.user.domain.repository.UserRemoteRepository

class UserRemoteRepositoryImpl(
    private val api: UserApi
) : UserRemoteRepository {

    override suspend fun fetchUsers(): List<UserInfo> {
        return api.fetchUsers().map { UserConverter.fromNetwork(response = it) }
    }
}