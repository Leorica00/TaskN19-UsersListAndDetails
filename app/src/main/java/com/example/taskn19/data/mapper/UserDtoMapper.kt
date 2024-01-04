package com.example.taskn19.data.mapper

import com.example.taskn19.data.model.UserDto
import com.example.taskn19.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id, email, firstName, lastName, avatar
    )
}