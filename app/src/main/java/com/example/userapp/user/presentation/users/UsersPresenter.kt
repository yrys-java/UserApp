package com.example.userapp.user.presentation.users

import com.example.userapp.core.ui.mvp.BasePresenter
import com.example.userapp.user.domain.interactor.UserInteractor
import kotlinx.coroutines.launch

class UsersPresenter(
    private val interactor: UserInteractor
): BasePresenter<UsersContract.View>(), UsersContract.Presenter {

    override fun attach(view: UsersContract.View) {
        super.attach(view)
        loadUsers(isRefresh = false)
    }

    override fun loadUsers(isRefresh: Boolean) {
        launch {
            try {
                view?.showLoading(isLoading = true)
                val users = interactor.fetchUsers(isRefresh)
                view?.showUsers(users)
            } catch (e: Throwable) {
                view?.showErrorMessage(e)
            } finally {
                view?.showLoading(isLoading = false)
            }
        }
    }
}