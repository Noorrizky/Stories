package com.example.myapplication.data

import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.data.pref.UserPreference
import com.example.myapplication.data.response.ListStoryItem
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.data.response.StoryResponse
import com.example.myapplication.data.retrofit.ApiService
import com.example.myapplication.di.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(
        name: String,
        email: String,
        password: String,
    ): Flow<ResultState<RegisterResponse>> = flow {
        try {
            val response = apiService.register(name, email, password)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    fun login(email: String, password: String): Flow<ResultState<LoginResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getStories(): Flow<ResultState<List<ListStoryItem>>> = flow {
        try {
            val response = apiService.getStories()
            val listStory = response.listStory?.filterNotNull() ?: emptyList()
            emit(ResultState.Success(listStory))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}
