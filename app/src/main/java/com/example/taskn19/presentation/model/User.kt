package com.example.taskn19.presentation.model

data class User(
    val id: Int = -1,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val isSelected: Boolean = false
)
