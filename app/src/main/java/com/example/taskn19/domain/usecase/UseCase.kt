package com.example.taskn19.domain.usecase

import com.example.taskn19.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase<P, R> {
    suspend fun execute(params: P): Flow<Resource<R>>
}


