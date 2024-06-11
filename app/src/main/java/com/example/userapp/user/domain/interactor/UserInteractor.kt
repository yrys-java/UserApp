package com.example.userapp.user.domain.interactor

import com.example.userapp.user.domain.model.UserInfo
import com.example.userapp.user.domain.repository.UserLocalRepository
import com.example.userapp.user.domain.repository.UserRemoteRepository

class UserInteractor(
    private val remoteRepository: UserRemoteRepository,
    private val localRepository: UserLocalRepository
) {

    suspend fun fetchUsers(isRefresh: Boolean): List<UserInfo> {
        if (isRefresh) {
            localRepository.deleteUsers()
        }

        if (localRepository.getUsers()?.isEmpty() == true) {
            val users = remoteRepository.fetchUsers()
            localRepository.insertUsers(users)
        }

        return localRepository.getUsers().orEmpty()
    }
}