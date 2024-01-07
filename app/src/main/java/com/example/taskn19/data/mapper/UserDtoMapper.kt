package com.example.taskn19.data.mapper

import com.example.taskn19.data.model.UserDto
import com.example.taskn19.domain.model.GetUser

fun UserDto.toDomain(): GetUser = GetUser(
    id, email, firstName, lastName, avatar
)