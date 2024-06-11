package com.example.userapp.core.ui.navigation

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.whenStateAtLeast
import com.example.userapp.R
import com.example.userapp.core.ui.keyboard.hideKeyboard
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

inline fun LifecycleOwner.whenStateAtLeast(state: Lifecycle.State, crossinline block: () -> Unit) {
    if (lifecycle.currentState.isAtLeast(state)) {
        block()
    } else {
        lifecycle.coroutineScope.launch {
            lifecycle.whenStateAtLeast(state) { block() }
        }
    }
}

fun Fragment.popBackStack(hideKeyboard: Boolean = true) {
    if (hideKeyboard) requireActivity().hideKeyboard()
    requireActivity().popBackStack()
}

fun FragmentActivity.popBackStack() {
    if (supportFragmentManager.backStackEntryCount <= 1) {
        finish()
    } else {
        supportFragmentManager.popBackStack()
    }
}

fun <T : Fragment> Fragment.popBackStackTo(target: KClass<T>, inclusive: Boolean = false) {
    requireActivity().popBackStackTo(target, inclusive)
}

fun <T : Fragment> FragmentActivity.popBackStackTo(target: KClass<T>, inclusive: Boolean = false) {
    val flag = if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0

    val tag = target.java.name
    with(supportFragmentManager) {
        if (backStackEntryCount == 0 || getBackStackEntryAt(0).name == tag && inclusive) {
            finish()
        } else {
            if (!popBackStackImmediate(tag, flag)) finish()
        }
    }
}

fun Fragment.popAndReplaceFragment(
    target: Fragment,
    popTo: KClass<out Fragment>? = null,
    @IdRes containerId: Int = R.id.content,
    addToBackStack: Boolean = true,
    inclusive: Boolean = false,
    @AnimRes enter: Int = R.anim.nav_enter,
    @AnimRes exit: Int = R.anim.nav_exit,
    @AnimRes popEnter: Int = R.anim.nav_pop_enter,
    @AnimRes popExit: Int = R.anim.nav_pop_exit,
    fragmentManager: FragmentManager = requireActivity().supportFragmentManager
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    with(fragmentManager) {
        popBackStack(
            popTo?.java?.name,
            if (inclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
        )

        commit(allowStateLoss = true) {
            // Preform immediate replace
            setCustomAnimations(enter, 0, popEnter, popExit)
            replace(containerId, target, target.javaClass.name)
            if (addToBackStack) addToBackStack(target.javaClass.name)
        }
    }
}

fun Fragment.addFragment(
    target: Fragment,
    @IdRes containerId: Int = R.id.content,
    addToBackStack: Boolean = true,
    @AnimRes enter: Int = R.anim.nav_enter,
    @AnimRes exit: Int = R.anim.nav_exit,
    @AnimRes popEnter: Int = R.anim.nav_pop_enter,
    @AnimRes popExit: Int = R.anim.nav_pop_exit,
    fragmentManager: FragmentManager = requireActivity().supportFragmentManager
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    fragmentManager.commit(allowStateLoss = true) {
        setCustomAnimations(enter, exit, popEnter, popExit)
        add(containerId, target, target.javaClass.name)
        if (addToBackStack) addToBackStack(target.javaClass.name)
    }
}

fun Fragment.replaceFragment(
    target: Fragment,
    @IdRes containerId: Int = R.id.content,
    addToBackStack: Boolean = true,
    @AnimRes enter: Int = R.anim.nav_enter,
    @AnimRes exit: Int = R.anim.nav_exit,
    @AnimRes popEnter: Int = R.anim.nav_pop_enter,
    @AnimRes popExit: Int = R.anim.nav_pop_exit,
    fragmentManager: FragmentManager = requireActivity().supportFragmentManager
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    fragmentManager.commit(allowStateLoss = true) {
        setCustomAnimations(enter, exit, popEnter, popExit)
        replace(containerId, target, target.javaClass.name)
        if (addToBackStack) addToBackStack(target.javaClass.name)
    }
}

fun FragmentActivity.replaceFragment(
    target: Fragment,
    @IdRes containerId: Int = R.id.content,
    addToBackStack: Boolean = true
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    supportFragmentManager.commit(allowStateLoss = true) {
        replace(containerId, target, target.javaClass.name)
        if (addToBackStack) addToBackStack(target.javaClass.name)
    }
}

fun FragmentActivity.addFragment(
    target: Fragment,
    @IdRes containerId: Int = R.id.content,
    addToBackStack: Boolean = true,
    @AnimRes enter: Int = R.anim.nav_enter,
    @AnimRes exit: Int = R.anim.nav_exit,
    @AnimRes popEnter: Int = R.anim.nav_pop_enter,
    @AnimRes popExit: Int = R.anim.nav_pop_exit
) = whenStateAtLeast(Lifecycle.State.STARTED) {
    supportFragmentManager.commit(allowStateLoss = true) {
        setCustomAnimations(enter, exit, popEnter, popExit)
        add(containerId, target, target.javaClass.name)
        if (addToBackStack) addToBackStack(target.javaClass.name)
    }
}