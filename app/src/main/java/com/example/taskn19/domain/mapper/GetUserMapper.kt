package com.example.taskn19.domain.mapper

import com.example.taskn19.domain.model.GetUser
import com.example.taskn19.presentation.model.User

fun GetUser.toPresenter(): User = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar
)