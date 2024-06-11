package com.example.userapp.root

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.userapp.R
import com.example.userapp.core.ui.navigation.popBackStack
import com.example.userapp.core.ui.navigation.replaceFragment
import com.example.userapp.user.presentation.users.UsersFragment

class RootActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RootActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        onBackPressedDispatcher.addCallback(this) {
           popBackStack()
        }
        replaceFragment(target = UsersFragment.create())
    }
}