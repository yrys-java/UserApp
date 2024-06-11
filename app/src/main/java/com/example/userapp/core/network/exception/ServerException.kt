package com.example.userapp.core.network.exception

import java.io.IOException

/**
 * Sealed class representing exceptions that can occur on the server.
 */
sealed class ServerException(
    override val message: String = "Что-то пошло не так. Повторите попытку!"
) : IOException(message) {

    // 429
    data object ManyRequestError : ServerException()

    // 502
    data object ServerNotAvailable : ServerException()

    // 503
    data object ServerDeploy : ServerException()

    // 404
    data class NotFoundError(
        override val message: String = "Ресурс не найден"
    ) : ServerException()

    // 403
    data object ForbiddenError : ServerException()

    // 401
    data object Unauthorized : ServerException()

    // 500
    data object InternalServerError : ServerException()

    data object NullPointResponseError : ServerException()

    data object UnknowError : ServerException()
}