package com.example.taskn19.data.mapper

import com.example.taskn19.data.model.UserDetailsResponseDto
import com.example.taskn19.domain.model.User

fun UserDetailsResponseDto.toDomain(): User = User(
    id = data.id,
    firstName = data.firstName,
    lastName = data.lastName,
    email = data.email,
    avatar = data.avatar
)