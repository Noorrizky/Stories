package com.example.myapplication.data.response

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult
)

data class LoginResult(
    val token: String,
    val userId: String,
    val  name: String

)



