package com.example.taskn19.data.common

sealed class Resource<T: Any>(
    val isLoading: Boolean = false,
    val successData: T? = null,
    val errorMessage: String = "",
    val error: Throwable? = null
) {
    data class Loading<T: Any>(val loading: Boolean, val loadingData: T? = null) : Resource<T>(
        isLoading = loading,
        successData = loadingData
    )
    data class Success<T: Any>(val response: T) : Resource<T>(successData = response)
    data class Error<T: Any>(val message: String, val throwable: Throwable) : Resource<T>(errorMessage = message, error = throwable)
}