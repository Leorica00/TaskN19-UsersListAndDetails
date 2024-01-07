package com.example.taskn19.di

import com.example.taskn19.data.common.HandleResponse
import com.example.taskn19.data.repository.UsersRepositoryImpl
import com.example.taskn19.data.service.UserDetailsApiService
import com.example.taskn19.data.service.UsersApiService
import com.example.taskn19.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(usersApiService: UsersApiService, userDetailsApiService: UserDetailsApiService):
            UsersRepository = UsersRepositoryImpl(
                usersApiService = usersApiService,
                userDetailsApiService = userDetailsApiService,
                handleResponse = HandleResponse()
            )
}