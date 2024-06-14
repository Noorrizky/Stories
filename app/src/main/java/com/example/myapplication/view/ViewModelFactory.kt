package com.example.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.UserRepository
import com.example.myapplication.di.Injection
import com.example.myapplication.view.main.MainViewModel
import com.example.myapplication.viewmodel.UserViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context)).also { INSTANCE = it }
            }
        }
    }
}
