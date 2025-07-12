package com.example.basecleararchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.domain.usecase.GetUsersUseCase
import com.example.basecleararchitecture.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    
    private val _users = MutableStateFlow<Resource<List<User>>>(Resource.Loading())
    val users: StateFlow<Resource<List<User>>> = _users
    
    init {
        getUsers()
    }
    
    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { resource ->
                _users.value = resource
            }
        }
    }
    
    fun refreshUsers() {
        getUsers()
    }
}