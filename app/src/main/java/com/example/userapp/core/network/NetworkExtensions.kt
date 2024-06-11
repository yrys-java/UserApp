package com.example.userapp.core.network

import com.example.userapp.core.network.exception.ServerException
import java.net.UnknownHostException

fun Throwable.getErrorMessage(): String {
        return when (this) {
            is UnknownHostException -> "Интернет соединение не найдено"
            is ServerException -> this.message
            else -> this.message ?: "Что-то пошло не так. Повторите попытку позже"
        }
    }