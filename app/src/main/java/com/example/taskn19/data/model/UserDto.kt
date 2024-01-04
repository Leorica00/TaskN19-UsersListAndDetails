package com.example.taskn19.data.model

import com.squareup.moshi.Json

data class UserDto(
    val id: Int,
    val email: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val avatar: String
)