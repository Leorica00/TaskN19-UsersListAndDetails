package com.example.taskn19.domain.usecase

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.model.User
import com.example.taskn19.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun getUsers(): Flow<Resource<List<User>>> {
        return usersRepository.getUsers()
    }
}