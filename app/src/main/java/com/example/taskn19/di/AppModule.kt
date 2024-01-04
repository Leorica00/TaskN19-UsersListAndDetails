package com.example.taskn19.di

import com.example.taskn19.Constants.USERS_BASE_URL
import com.example.taskn19.Constants.USER_DETAILS_BASE_URL
import com.example.taskn19.data.service.UserDetailsApiService
import com.example.taskn19.data.service.UsersApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UsersRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserDetailsRetrofit



    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return  Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    @UsersRetrofit
    fun provideUsersRetrofitClient(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USERS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    @UserDetailsRetrofit
    fun provideUserDetailsRetrofitClient(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USER_DETAILS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersApiService(@UsersRetrofit retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDetailsApiService(@UserDetailsRetrofit retrofit: Retrofit): UserDetailsApiService {
        return retrofit.create(UserDetailsApiService::class.java)
    }
}