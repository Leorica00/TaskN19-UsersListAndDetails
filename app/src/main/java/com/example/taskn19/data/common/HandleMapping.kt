package com.example.taskn19.data.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T: Any, R: Any> Flow<Resource<T>>.mapResource(mapper: (T) -> R): Flow<Resource<R>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Success -> Resource.Success(mapper(resource.successData!!))
            is Resource.Error -> Resource.Error(resource.errorMessage, resource.throwable)
            is Resource.Loading -> Resource.Loading(resource.loading)
        }
    }
}
