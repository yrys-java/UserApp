package com.example.userapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.userapp.core.db.UserAppDatabase.Companion.DATABASE_VERSION
import com.example.userapp.user.data.db.UserDao
import com.example.userapp.user.data.db.entity.UserInfoEntity


@Database(
    entities = [
        UserInfoEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class UserAppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
    }

    abstract fun userDao(): UserDao
}