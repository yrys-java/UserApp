package com.example.userapp.user.di

import com.example.userapp.core.db.UserAppDatabase
import com.example.userapp.core.di.InjectionModule
import com.example.userapp.user.data.api.UserApi
import com.example.userapp.user.data.repository.UserRemoteRepositoryImpl
import com.example.userapp.user.data.repository.UserLocalRepositoryImpl
import com.example.userapp.user.domain.interactor.UserInteractor
import com.example.userapp.user.domain.repository.UserLocalRepository
import com.example.userapp.user.domain.repository.UserRemoteRepository
import com.example.userapp.user.presentation.users.UsersContract
import com.example.userapp.user.presentation.users.UsersPresenter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object UserModule : InjectionModule {

    override fun create() = module {
        single { get<Retrofit>().create(UserApi::class.java) }
        single { get<UserAppDatabase>().userDao() }
        singleOf(::UserRemoteRepositoryImpl) bind  UserRemoteRepository::class
        singleOf(::UserLocalRepositoryImpl) bind UserLocalRepository::class
        singleOf(::UserInteractor)
        singleOf(::UsersPresenter) bind UsersContract.Presenter::class
    }
}