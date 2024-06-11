package com.example.userapp.core.ui.mvp

import androidx.annotation.CallSuper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>, CoroutineScope {

    override val coroutineContext: CoroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler(::handleCoroutineException)

    protected var view: V? = null
        private set

    @CallSuper
    override fun attach(view: V) {
        this.view = view
    }

    @CallSuper
    override fun detach() {
        coroutineContext.cancelChildren()
        view = null
    }

    private fun handleCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        if (throwable is CancellationException) {
            Timber.i(throwable)
        } else {
            Timber.tag(this::class.java.canonicalName).e(throwable, coroutineContext.toString())
        }
    }
}
