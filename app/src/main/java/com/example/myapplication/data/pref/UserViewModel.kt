package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.RegisterResponse
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String, onSuccess: (RegisterResponse) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.register(name, email, password)
                onSuccess(response)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun login(email: String, password: String, onSuccess: (LoginResponse) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(email, password)
                onSuccess(response)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
