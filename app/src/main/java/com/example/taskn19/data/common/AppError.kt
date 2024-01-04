package com.example.taskn19.data.common

import android.util.Log
import com.example.taskn19.data.model.ErrorResponseDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeoutException

sealed class AppError(open val message: String) {
    data class NetworkError(override val message: String) : AppError(message)
    data class HttpError(override val message: String) : AppError(message)
    data class TimeoutError(override val message: String) : AppError(message)
    data class ServerError(override val message: String) : AppError(message)
    data class ClientError(override val message: String) : AppError(message)
    data class UnknownError(override val message: String) : AppError(message)

    companion object {
        fun fromException(t: Throwable): AppError {
            val adapter: JsonAdapter<ErrorResponseDto> = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(ErrorResponseDto::class.java)
            return when (t) {
                is IOException -> {
                    Logger.logError(t.stackTraceToString())
                    NetworkError("Network error occurred: No Internet")
                }
                is HttpException -> {
                    val errorModel = t.response()?.errorBody()?.string()?.let { adapter.fromJson(it) }
                    when (t.code()) {
                        in 400..499 -> {
                            Logger.logError(t.stackTraceToString())
                            ClientError(errorModel?.error.toString())
                        }
                        in 500..599 -> {
                            Logger.logError(t.stackTraceToString())
                            ServerError("Server error occurred")
                        }
                        else -> {
                            Logger.logError(t.stackTraceToString())
                            HttpError("Http error occurred")
                        }
                    }
                }
                is TimeoutException -> {
                    Logger.logError(t.stackTraceToString())
                    TimeoutError("Can not process task")
                }
                else -> {
                    Logger.logError(t.stackTraceToString())
                    UnknownError("An unexpected error occurred")
                }
            }
        }
    }

    private object Logger {
        private const val TAG = "ErrorsLog"

        fun logError(error: String) {
            val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val logMessage = "Time: $timestamp Error: $error"
            Log.e(TAG, logMessage)
        }
    }
}