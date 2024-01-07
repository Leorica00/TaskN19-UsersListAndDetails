package com.example.taskn19.presentation.user_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskn19.data.common.Resource
import com.example.taskn19.domain.usecase.UserDetailsUseCase
import com.example.taskn19.presentation.model.User
import com.example.taskn19.presentation.user_details.event.UserDetailsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val userDetailsUseCase: UserDetailsUseCase) :
    ViewModel() {

    private val _userDetailsStateFlow: MutableStateFlow<Resource<User>> =
        MutableStateFlow(Resource.Loading(false))
    val userDetailsStateFlow get() = _userDetailsStateFlow.asStateFlow()

    fun onEvent(event: UserDetailsEvent) {
        when (event) {
            is UserDetailsEvent.GetUserDetailsEvent -> getUserDetails(event.userId)
        }
    }

    private fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            userDetailsUseCase(userId = userId).collect {
                _userDetailsStateFlow.value = when (it) {
                    is Resource.Loading -> Resource.Loading(it.loading)
                    is Resource.Success -> Resource.Success(it.successData!!)
                    is Resource.Error -> Resource.Error(it.errorMessage, it.error!!)
                }
            }
        }
    }
}