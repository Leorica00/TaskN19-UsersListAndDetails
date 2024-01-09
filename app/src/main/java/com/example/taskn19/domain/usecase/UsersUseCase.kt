package com.example.taskn19.domain.usecase

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.common.mapResource
import com.example.taskn19.domain.mapper.toPresenter
import com.example.taskn19.domain.repository.UsersRepository
import com.example.taskn19.presentation.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(): Flow<Resource<List<User>>> {
        return usersRepository.getUsers()
            .mapResource { users -> users.map { it.toPresenter() } }
    }
}