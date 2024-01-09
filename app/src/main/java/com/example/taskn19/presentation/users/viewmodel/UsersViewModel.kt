package com.example.taskn19.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.usecase.DeleteUserUseCase
import com.example.taskn19.domain.usecase.UsersUseCase
import com.example.taskn19.presentation.model.User
import com.example.taskn19.presentation.users.event.UsersEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<Resource<List<User>>>(Resource.Loading(false))
    val usersStateFlow get() = _usersStateFlow.asStateFlow()

    fun onEvent(event: UsersEvent): Boolean {
        when (event) {
            is UsersEvent.FetchData -> setData()
            is UsersEvent.SelectUser -> selectUser(userId = event.id)
            is UsersEvent.DeleteSelectedUsers -> deleteSelectedUsers()
            is UsersEvent.CheckIfAnyUserIsChecked -> return checkIfAnyUserIsChecked()
        }
        return false
    }

    private fun setData() {
        viewModelScope.launch {
            usersUseCase().collect {
                _usersStateFlow.value = when (it) {
                    is Resource.Loading -> Resource.Loading(it.loading)
                    is Resource.Success -> Resource.Success(it.successData!!)
                    is Resource.Error -> Resource.Error(it.errorMessage, it.error!!)
                }
            }
        }
    }

    private fun selectUser(userId: Int) {
        _usersStateFlow.value = Resource.Success(_usersStateFlow.value.successData!!.map { user ->
            if (user.id == userId) {
                user.copy(isSelected = !user.isSelected)
            } else {
                user
            }
        }
        )
    }

    private fun deleteSelectedUsers() {
        val selectedUsers = _usersStateFlow.value.successData!!.filter { user -> user.isSelected }
        viewModelScope.launch {
            selectedUsers.forEach { user ->
                deleteUserUseCase(userId = user.id).collect {
                    when (it) {
                        is Resource.Success -> _usersStateFlow.value =
                            Resource.Success(_usersStateFlow.value.successData!!.filter { selectedUser -> selectedUser.id != user.id })

                        is Resource.Error -> _usersStateFlow.value =
                            Resource.Error(it.errorMessage, it.error!!)

                        is Resource.Loading -> _usersStateFlow.value =
                            Resource.Loading(it.isLoading, _usersStateFlow.value.successData)
                    }
                }
            }
        }
    }

    private fun checkIfAnyUserIsChecked(): Boolean {
        return _usersStateFlow.value.successData!!.any { it.isSelected }
    }
}