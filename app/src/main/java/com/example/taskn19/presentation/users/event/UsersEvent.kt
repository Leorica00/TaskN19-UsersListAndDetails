package com.example.taskn19.presentation.users.event

sealed class UsersEvent {
    data object FetchData : UsersEvent()
    data class SelectUser(val id: Int) : UsersEvent()
    data object DeleteSelectedUsers : UsersEvent()
    data object CheckIfAnyUserIsChecked : UsersEvent()
}