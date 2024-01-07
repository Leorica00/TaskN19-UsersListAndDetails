package com.example.taskn19.domain.common

import com.example.taskn19.data.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, PresenterType> Flow<Resource<T>>.mapToPresenter(mapper: (T?) -> PresenterType): Flow<Resource<PresenterType>> {
    return this.map { resource ->
        when (resource) {
            is Resource.Error -> Resource.Error(resource.errorMessage, resource.throwable)
            is Resource.Loading -> Resource.Loading(resource.loading)
            is Resource.Success -> Resource.Success(mapper(resource.successData))
        }
    }
}