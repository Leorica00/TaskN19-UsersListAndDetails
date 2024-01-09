package com.example.taskn19.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockyUsersRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReqresUsersRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockyUsersService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReqresUsersService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MockyUsersRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReqresUsersRepository