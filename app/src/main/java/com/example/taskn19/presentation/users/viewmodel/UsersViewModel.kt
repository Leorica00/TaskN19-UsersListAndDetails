package com.example.taskn19.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.model.User
import com.example.taskn19.domain.usecase.UsersUseCase
import com.example.taskn19.presentation.users.event.UsersEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersUseCase: UsersUseCase) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<Resource<List<User>>>(Resource.Loading(false))
    val usersStateFlow get() = _usersStateFlow.asStateFlow()

    fun onEvent(event: UsersEvent) {
        when (event) {
            UsersEvent.FetchData -> setData()
        }
    }

    private fun setData() {
        viewModelScope.launch {
            usersUseCase.getUsers().collect {
                _usersStateFlow.value = when (it) {
                    is Resource.Loading -> Resource.Loading(it.loading)
                    is Resource.Success -> Resource.Success(it.successData!!)
                    is Resource.Error -> Resource.Error(it.errorMessage, it.error!!)
                }
            }
        }
    }
}