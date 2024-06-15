package com.example.myapplication.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (!response.error) {
                    val user = UserModel(email, response.loginResult.token, true)
                    saveSession(user)
                    onResult(true, response.message)
                } else {
                    onResult(false, response.message)
                }
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string()
                onResult(false, errorMessage ?: "An error occurred")
            }
        }
    }
}
