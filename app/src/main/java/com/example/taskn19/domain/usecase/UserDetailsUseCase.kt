package com.example.taskn19.domain.usecase

import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.common.mapResource
import com.example.taskn19.domain.mapper.toPresenter
import com.example.taskn19.domain.repository.UsersRepository
import com.example.taskn19.presentation.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsUseCase @Inject constructor(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(userId: Int): Flow<Resource<User>> {
        return usersRepository.getUserDetails(userId).mapResource { it.toPresenter() }
    }
}