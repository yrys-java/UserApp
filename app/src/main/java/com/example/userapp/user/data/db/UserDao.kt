package com.example.userapp.user.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.userapp.user.data.db.entity.UserInfoEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<UserInfoEntity>)

    @Query("DELETE FROM user_info")
    suspend fun delete()

    @Query("SELECT * FROM user_info")
    suspend fun getEntity(): List<UserInfoEntity>?
}