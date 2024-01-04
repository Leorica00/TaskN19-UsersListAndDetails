package com.example.taskn19.presentation.users.event

sealed class UsersEvent {
    data object FetchData : UsersEvent()
}