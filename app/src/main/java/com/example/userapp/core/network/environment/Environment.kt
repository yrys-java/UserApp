package com.example.userapp.core.network.environment

import com.example.userapp.BuildConfig

data class Environment(
    val baseAddress: String,
    val isSslEnabled: Boolean = true,
) {
    val restAddress: String = "${if (isSslEnabled) "https" else "http"}://$baseAddress/"

    companion object {
        private val DEBUG = Environment(
            baseAddress = "jsonplaceholder.typicode.com",
            isSslEnabled = true,
        )

        private val RELEASE = Environment(
            baseAddress = "jsonplaceholder.typicode.com",
            isSslEnabled = true,
        )

        fun getBuildVariantEnvironment(): Environment {
            return if (BuildConfig.DEBUG) DEBUG else RELEASE
        }
    }
}