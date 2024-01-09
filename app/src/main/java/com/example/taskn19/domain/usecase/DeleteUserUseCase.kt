package com.example.taskn19.domain.usecase

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(userId: Int): Flow<Resource<Unit>> {
        return usersRepository.deleteUser(userId)
    }
}