package com.example.taskn19.domain.repository

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsers(): Flow<Resource<List<User>>>
    suspend fun getUserDetails(userId: Int): Flow<Resource<User>>
}