package com.example.userapp.core.network.environment

import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_BASE_URL = "KEY_BASE_URL"
private const val SSL_ENABLED_KEY = "SSL_ENABLED_KEY"

class EnvironmentManager(
    private val environment: Environment,
) {

    fun loadEnvironment(
        sharedPreferences: SharedPreferences
    ) = with(sharedPreferences) {
        Environment(
            baseAddress = getString(KEY_BASE_URL, environment.baseAddress).orEmpty(),
            isSslEnabled = getBoolean(SSL_ENABLED_KEY, environment.isSslEnabled)
        )
    }

    fun saveEnvironment(
        sharedPreferences: SharedPreferences,
        environment: Environment
    ) {
        sharedPreferences.edit {
            putString(KEY_BASE_URL, environment.baseAddress)
        }
    }
}