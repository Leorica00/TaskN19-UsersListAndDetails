package com.example.taskn19.di

import com.example.taskn19.data.common.HandleResponse
import com.example.taskn19.data.repository.UsersRepositoryImpl
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
    @MockyUsersRepository
    fun provideUsersRepository(@MockyUsersService usersApiService: UsersApiService): UsersRepository =
        UsersRepositoryImpl(
                usersApiService = usersApiService,
                handleResponse = HandleResponse()
            )

    @Singleton
    @Provides
    @ReqresUsersRepository
    fun provideUserRepository(@ReqresUsersService usersApiService: UsersApiService): UsersRepository = UsersRepositoryImpl(
        usersApiService = usersApiService,
        handleResponse = HandleResponse()
    )
}