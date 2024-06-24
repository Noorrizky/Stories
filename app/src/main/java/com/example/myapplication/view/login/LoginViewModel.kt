package com.example.myapplication.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.di.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.login(email, password).collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        val response = result.data
                        if (!response.error) {
                            val user = UserModel(email, response.loginResult.token, true)
                            saveSession(user)
                            onResult(true, response.message)
                        } else {
                            onResult(false, response.message)
                        }
                    }
                    is ResultState.Error -> {
                        onResult(false, result.error)
                    }
                    is ResultState.Loading -> {

                    }
                }
            }
        }
    }
}
