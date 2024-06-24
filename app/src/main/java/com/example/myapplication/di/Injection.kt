package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.pref.UserPreference
import com.example.myapplication.data.pref.dataStore
import com.example.myapplication.data.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}
