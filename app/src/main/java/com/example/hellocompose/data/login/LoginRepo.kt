package com.example.hellocompose.data.login

import com.example.hellocompose.data.login.model.UserAccount
import kotlinx.coroutines.flow.Flow
import com.example.hellocompose.ui.util.Result

interface LoginRepo {
    suspend fun isUserLoggedIn():Flow<Result<Boolean>>
    suspend fun signIn(userAccount: UserAccount):Flow<Result<String>>
    suspend fun getToken(smsCode: String):Flow<Result<Unit>>
    suspend fun resendSMS():Result<Unit>
}