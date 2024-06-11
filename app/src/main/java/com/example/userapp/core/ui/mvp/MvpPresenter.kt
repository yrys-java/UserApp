package com.example.userapp.core.ui.mvp

/**
 * @see BasePresenter
 */
interface MvpPresenter<V : MvpView> {
    /**
     * called on each `onViewCreated` from View
     */
    fun attach(view: V)

    fun detach()
}
