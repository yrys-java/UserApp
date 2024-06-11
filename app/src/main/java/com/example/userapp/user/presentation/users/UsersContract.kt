package com.example.userapp.user.presentation.users

import com.example.userapp.core.ui.mvp.MvpPresenter
import com.example.userapp.core.ui.mvp.MvpView
import com.example.userapp.user.domain.model.UserInfo

interface UsersContract {

    interface View : MvpView {
        fun showLoading(isLoading: Boolean)
        fun showUsers(users: List<UserInfo>)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadUsers(isRefresh: Boolean)
    }
}