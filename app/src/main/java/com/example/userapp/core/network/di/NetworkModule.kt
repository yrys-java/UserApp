package com.example.userapp.core.network.di

import android.annotation.SuppressLint
import com.example.userapp.BuildConfig
import com.example.userapp.core.di.InjectionModule
import com.example.userapp.core.network.adapter.DateTimeTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.example.userapp.core.network.environment.Environment
import com.example.userapp.core.network.environment.EnvironmentManager
import com.example.userapp.core.network.interceptor.ServerErrorInterceptor
import com.example.userapp.core.network.manager.NetworkConnectivityService
import com.example.userapp.core.network.manager.NetworkConnectivityServiceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object NetworkModule : InjectionModule {
    private const val DEFAULT_CONNECT_TIMEOUT_SECONDS = 60L
    private const val DEFAULT_READ_TIMEOUT_SECONDS = 60L

    override fun create() = module {
        single { EnvironmentManager(Environment.getBuildVariantEnvironment()) }
        single { get<EnvironmentManager>().loadEnvironment(get()) } bind Environment::class

        single {
            GsonBuilder()
                .registerTypeAdapter(Calendar::class.java, DateTimeTypeAdapter)
                .apply { if (BuildConfig.DEBUG) setPrettyPrinting() }
                .create()
        }

        single {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(get()))
                .baseUrl(get<Environment>().restAddress)
                .client(get())
                .build()
        }

        single {
            createOkHttpClient()
                .apply { if (BuildConfig.DEBUG) addInterceptor(createLoggingInterceptor()) }
                .build()
        }
        singleOf(::NetworkConnectivityServiceImpl) bind NetworkConnectivityService::class
    }

    private fun Scope.createLoggingInterceptor(tag: String? = null): HttpLoggingInterceptor {
        val gson = get<Gson>()
        val okHttpLogTag = if (tag.isNullOrEmpty()) "OkHttp" else tag

        val okHttpLogger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith('{') && !message.startsWith('[')) {
                    Timber.tag(okHttpLogTag).d(message)
                    return
                }

                try {
                    val json = JsonParser().parse(message)
                    Timber.tag(okHttpLogTag).d(gson.toJson(json))
                } catch (e: Throwable) {
                    Timber.tag(okHttpLogTag).d(message)
                }
            }
        }
        return HttpLoggingInterceptor(okHttpLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @SuppressLint("CustomX509TrustManager")
    private fun Scope.createOkHttpClient(): OkHttpClient.Builder {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .pingInterval(3, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(ServerErrorInterceptor())
            .apply {
                sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                hostnameVerifier { _, _ -> true }
            }
    }
}
