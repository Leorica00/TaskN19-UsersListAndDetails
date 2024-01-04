package com.example.taskn19.presentation.user_details.event

sealed class UserDetailsEvent {
    data class GetUserDetailsEvent(val userId: Int): UserDetailsEvent()
}