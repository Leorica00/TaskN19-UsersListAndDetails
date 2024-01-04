package com.example.taskn19.data.service

import com.example.taskn19.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {
    @GET("7ec14eae-06bf-4f6d-86d2-ac1b9df5fe3d")
    suspend fun getUsers(): Response<List<UserDto>>
}