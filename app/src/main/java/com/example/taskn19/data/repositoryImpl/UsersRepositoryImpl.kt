package com.example.taskn19.data.repositoryImpl

import com.example.taskn19.data.common.HandleResponse
import com.example.taskn19.data.common.Resource
import com.example.taskn19.data.mapper.toDomain
import com.example.taskn19.data.service.UserDetailsApiService
import com.example.taskn19.data.service.UsersApiService
import com.example.taskn19.domain.model.User
import com.example.taskn19.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val userDetailsApiService: UserDetailsApiService,
    private val handleResponse: HandleResponse
) : UsersRepository {

    override suspend fun getUsers(): Flow<Resource<List<User>>> {
        return handleResponse.safeApiCall {
            usersApiService.getUsers()
        }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.successData!!.map { it.toDomain() })
                is Resource.Loading -> Resource.Loading(resource.loading)
                is Resource.Error -> Resource.Error(resource.errorMessage, resource.throwable)
            }
        }
    }

    override suspend fun getUserDetails(userId: Int): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            userDetailsApiService.getUser(userId)
        }.map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.successData!!.toDomain())
                is Resource.Loading -> Resource.Loading(resource.loading)
                is Resource.Error -> Resource.Error(resource.errorMessage, resource.throwable)
            }
        }
    }
}