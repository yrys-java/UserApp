package com.example.userapp.core.network.interceptor

import com.example.userapp.core.network.exception.ServerException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

/**
 * Interceptor for handling server errors during network requests.
 */
class ServerErrorInterceptor : Interceptor {

    /**
     * Intercepts the network request and handles server errors.
     *
     * @param chain The interceptor chain.
     * @return The response if successful, or throws an appropriate [ServerException] for encountered errors.
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = try {
        val request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.peekBody(Long.MAX_VALUE).string()

        when (response.code) {
            401 -> throw ServerException.Unauthorized
            403 -> throw ServerException.ForbiddenError
            404 -> throw ServerException.NotFoundError()
            429 -> throw ServerException.ManyRequestError
            500 -> throw ServerException.InternalServerError
            502 -> throw ServerException.ServerNotAvailable
            503 -> throw ServerException.ServerDeploy
        }

        if (responseBody.isBlank()) {
            throw ServerException.NullPointResponseError
        }

        if (!response.isSuccessful) {
            throw ServerException.UnknowError
        }

        // avoid all negative case return Response
        response
    } catch (exception: ServerException) {
        Timber.tag("ServerErrorInterceptor").e("ServerException: Error handled and exception")
        throw exception
    } catch (throwable: Throwable) {
        Timber.tag("ServerErrorInterceptor").e("Throwable: Error handled and thrown")
        throw throwable
    }
}