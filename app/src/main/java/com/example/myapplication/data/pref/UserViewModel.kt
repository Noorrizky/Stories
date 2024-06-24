package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.di.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String, onSuccess: (RegisterResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            userRepository.register(name, email, password).collect { result ->
                when (result) {
                    is ResultState.Success -> onSuccess(result.data)
                    is ResultState.Error -> onError(result.error)
                    is ResultState.Loading -> { /* Handle loading state if needed */ }
                }
            }
        }
    }

    fun login(email: String, password: String, onSuccess: (LoginResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            userRepository.login(email, password).collect { result ->
                when (result) {
                    is ResultState.Success -> onSuccess(result.data)
                    is ResultState.Error -> onError(result.error)
                    is ResultState.Loading -> { /* Handle loading state if needed */ }
                }
            }
        }
    }
}
