package com.example.taskn19.data.service

import com.example.taskn19.data.model.UserDetailsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailsApiService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): Response<UserDetailsResponseDto>
}