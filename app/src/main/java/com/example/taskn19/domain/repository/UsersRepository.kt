package com.example.taskn19.domain.repository

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.model.GetUser
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsers(): Flow<Resource<List<GetUser>>>
    suspend fun getUserDetails(userId: Int): Flow<Resource<GetUser>>
    suspend fun deleteUser(userId: Int): Flow<Resource<Unit>>
}