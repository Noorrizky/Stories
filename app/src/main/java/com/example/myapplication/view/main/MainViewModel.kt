package com.example.myapplication.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.response.ListStoryItem
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData
import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.di.ResultState
import kotlinx.coroutines.flow.first

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getStories(token: String) {
        viewModelScope.launch {
            when (val result = userRepository.getStories().first()) {
                is ResultState.Success -> {
                    _stories.value = result.data
                }

                else -> {

                }
            }
        }
    }
}
