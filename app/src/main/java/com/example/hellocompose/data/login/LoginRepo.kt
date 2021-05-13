package com.example.hellocompose.data.login

import kotlinx.coroutines.flow.Flow
import com.example.hellocompose.ui.util.Result

interface LoginRepo {
    suspend fun isUserLoggedIn():Flow<Result<Boolean>>
    suspend fun login(model: String):Flow<Result<Int>>
}