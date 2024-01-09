package com.example.taskn19.di

import com.example.taskn19.BuildConfig
import com.example.taskn19.data.service.UsersApiService
import com.example.taskn19.domain.repository.UsersRepository
import com.example.taskn19.domain.usecase.DeleteUserUseCase
import com.example.taskn19.domain.usecase.UserDetailsUseCase
import com.example.taskn19.domain.usecase.UsersUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return  Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @MockyUsersRetrofit
    fun provideUsersRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.USERS_MOCKY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    @ReqresUsersRetrofit
    fun provideUserDetailsRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.USERS_REQRES_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @MockyUsersService
    fun provideUsersApiService(@MockyUsersRetrofit retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }

    @Provides
    @Singleton
    @ReqresUsersService
    fun provideUserDetailsApiService(@ReqresUsersRetrofit retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersUseCase(@MockyUsersRepository usersRepository: UsersRepository): UsersUseCase {
        return UsersUseCase(usersRepository)
    }

    @Provides
    @Singleton
    fun provideUserDetailsUseCase(@ReqresUsersRepository usersRepository: UsersRepository): UserDetailsUseCase {
        return UserDetailsUseCase(usersRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(@ReqresUsersRepository usersRepository: UsersRepository): DeleteUserUseCase {
        return DeleteUserUseCase(usersRepository)
    }
}