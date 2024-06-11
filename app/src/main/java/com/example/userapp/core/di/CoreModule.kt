package com.example.userapp.core.di

import android.content.Context
import org.koin.dsl.module

object CoreModule : InjectionModule {

    override fun create() = module {
        single { get<Context>().resources }
        single {
            val context = get<Context>()
            val name = "${context.packageName}.prefs"
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }
}
