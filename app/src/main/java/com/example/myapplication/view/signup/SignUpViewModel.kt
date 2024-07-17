package com.example.myapplication.view.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class SignUpViewModel(application: UserRepository) : AndroidViewModel(Application()) {
    private val _registrationResult = MutableLiveData<RegisterResponse?>()
    val registrationResult: LiveData<RegisterResponse?> get() = _registrationResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService("")
                val response = apiService.register(name, email, password)
                _registrationResult.postValue(response)
            } catch (e: Exception) {
                _registrationResult.postValue(null)
            }
        }
    }

    fun saveSession(userModel: UserModel) {
    }
}
