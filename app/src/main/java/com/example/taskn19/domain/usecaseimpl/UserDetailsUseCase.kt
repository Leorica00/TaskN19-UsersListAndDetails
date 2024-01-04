package com.example.taskn19.domain.usecaseimpl

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.model.User
import com.example.taskn19.domain.repository.UsersRepository
import com.example.taskn19.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsUseCase @Inject constructor(private val usersRepository: UsersRepository) :
    UseCase<Int, User> {
    override suspend fun execute(params: Int): Flow<Resource<User>> {
        return usersRepository.getUserDetails(params)
    }
}