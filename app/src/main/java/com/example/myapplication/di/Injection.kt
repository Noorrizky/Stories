package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.UserRepository
import com.example.myapplication.data.pref.UserPreference
import com.example.myapplication.data.pref.dataStore
import com.example.myapplication.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.create()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref, apiService)
    }
}
