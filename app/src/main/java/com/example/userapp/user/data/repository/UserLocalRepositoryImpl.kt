package com.example.userapp.user.data.repository

import com.example.userapp.user.data.db.UserDao
import com.example.userapp.user.domain.model.UserInfo
import com.example.userapp.user.domain.repository.UserLocalRepository

class UserLocalRepositoryImpl(
    private val dao: UserDao
): UserLocalRepository {


    override suspend fun insertUsers(users: List<UserInfo>) {
        val entity = users.map { UserConverter.toEntity(it) }
        dao.insert(entity)
    }

    override suspend fun getUsers(): List<UserInfo>? {
        val entity = dao.getEntity()
        return entity?.map { UserConverter.fromEntity(it) }
    }

    override suspend fun deleteUsers() {
        dao.delete()
    }
}