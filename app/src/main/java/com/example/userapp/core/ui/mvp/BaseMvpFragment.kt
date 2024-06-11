package com.example.userapp.core.ui.mvp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.userapp.core.network.exception.ServerException
import com.example.userapp.core.network.getErrorMessage
import java.net.UnknownHostException

abstract class BaseMvpFragment<V : MvpView, P : MvpPresenter<V>>(
    @LayoutRes layoutRes: Int
) : Fragment(layoutRes), MvpView {

    abstract val presenter: P

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.attach(this as V)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun showErrorMessage(messageResId: Int) {
        Toast.makeText(this@BaseMvpFragment.context, getString(messageResId), Toast.LENGTH_LONG)
            .show()
    }

    override fun showErrorMessage(e: Throwable) {
        val message = e.getErrorMessage()
        Toast.makeText(this@BaseMvpFragment.context, message, Toast.LENGTH_LONG).show()
    }
}
