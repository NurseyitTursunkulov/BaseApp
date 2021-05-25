package com.example.hellocompose.data.login.model

data class Token(
    val refreshToken: String,
    val accessToken: String
)

data class TokenRequest(
    val clientName: String,
    val password: String
)