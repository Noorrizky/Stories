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
            try {
                val response = userRepository.getStories(token)
                _stories.value = response.listStory as List<ListStoryItem>?
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
