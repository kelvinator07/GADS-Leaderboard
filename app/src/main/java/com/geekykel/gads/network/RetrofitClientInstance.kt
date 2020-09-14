package com.geekykel.gads.network

import com.geekykel.gads.model.ErrorResponse
import com.geekykel.gads.model.ResultWrapper
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


private const val BASE_URL: String = "https://gadsapi.herokuapp.com"
private const val GOOGLE_SHEET_URL: String = "https://docs.google.com"

private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client by lazy {
    OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

val googleSheetRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(GOOGLE_SHEET_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun <T> buildService(service: Class<T>): T {
    return retrofit.create(service)
}

fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {

        throwable.response()?.errorBody()?.let {
            val gson = Gson()
            val error: ErrorResponse = gson.fromJson(
                it.string(),
                ErrorResponse::class.java
            )
            error
        }

    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Deferred<T>
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke().await())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                is IOException -> ResultWrapper.NetworkError
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}


