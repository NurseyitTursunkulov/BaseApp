package com.example.hellocompose.data.login

import com.example.hellocompose.data.login.model.UserAccount
import kotlinx.coroutines.flow.Flow
import com.example.hellocompose.ui.util.Result

interface LoginRepo {
    suspend fun isUserLoggedIn():Flow<Result<Boolean>>
    suspend fun login(model: UserAccount):Flow<Result<String>>
}