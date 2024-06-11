package com.example.userapp.core.db

import androidx.room.Room
import com.example.userapp.BuildConfig
import com.example.userapp.core.di.InjectionModule
import org.koin.dsl.module

object DatabaseModule: InjectionModule {

    override fun create() = module {
        single {
            Room.databaseBuilder(get(), UserAppDatabase::class.java, BuildConfig.APPLICATION_ID)
                .build()
        }
    }
}