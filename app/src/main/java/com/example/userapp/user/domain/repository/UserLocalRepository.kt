package com.example.userapp.user.domain.repository

import com.example.userapp.user.domain.model.UserInfo

interface UserLocalRepository {

    suspend fun insertUsers(users: List<UserInfo>)

    suspend fun getUsers(): List<UserInfo>?

    suspend fun deleteUsers()
}