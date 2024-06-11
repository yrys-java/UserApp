package com.example.userapp.core.ui.mvp

import androidx.annotation.StringRes

interface MvpView {
    fun showErrorMessage(e: Throwable)
    fun showErrorMessage(@StringRes messageResId: Int)
}
