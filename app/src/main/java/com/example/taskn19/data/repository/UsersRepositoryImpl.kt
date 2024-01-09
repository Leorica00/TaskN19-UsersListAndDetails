package com.example.taskn19.data.repository

import com.example.taskn19.data.common.AppError
import com.example.taskn19.data.common.HandleResponse
import com.example.taskn19.data.common.Resource
import com.example.taskn19.data.common.mapResource
import com.example.taskn19.data.mapper.toDomain
import com.example.taskn19.data.service.UsersApiService
import com.example.taskn19.domain.model.GetUser
import com.example.taskn19.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApiService: UsersApiService,
    private val handleResponse: HandleResponse
) : UsersRepository {

    override suspend fun getUsers(): Flow<Resource<List<GetUser>>> {
        return handleResponse.safeApiCall {
            usersApiService.getUsers()
        }.mapResource { users ->
            users.map { it.toDomain() }
        }
    }

    override suspend fun getUserDetails(userId: Int): Flow<Resource<GetUser>> {
        return handleResponse.safeApiCall {
            usersApiService.getUser(userId)
        }.mapResource {
            it.data.toDomain()
        }
    }

    override suspend fun deleteUser(userId: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = usersApiService.deleteUser(userId)
            if (response.isSuccessful) {
                emit(Resource.Success(response = Unit))
            } else {
                throw HttpException(response)
            }
        } catch (t: Throwable) {
            emit(Resource.Error(message = AppError.fromException(t).message, throwable = t))
        }
    }
}
