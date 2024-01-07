package com.example.taskn19.data.repository

import com.example.taskn19.data.common.HandleResponse
import com.example.taskn19.data.common.Resource
import com.example.taskn19.data.common.mapToDomain
import com.example.taskn19.data.mapper.toDomain
import com.example.taskn19.data.service.UserDetailsApiService
import com.example.taskn19.data.service.UsersApiService
import com.example.taskn19.domain.model.GetUser
import com.example.taskn19.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val userDetailsApiService: UserDetailsApiService,
    private val handleResponse: HandleResponse
) : UsersRepository {

    override suspend fun getUsers(): Flow<Resource<List<GetUser>>> {
        return handleResponse.safeApiCall {
            usersApiService.getUsers()
        }.mapToDomain {users ->
            users?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun getUserDetails(userId: Int): Flow<Resource<GetUser>> {
        return handleResponse.safeApiCall {
            userDetailsApiService.getUser(userId)
        }.mapToDomain {
            it?.data?.toDomain() ?: GetUser(-1, "", "", "", "")
        }
    }
}