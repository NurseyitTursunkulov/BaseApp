package com.example.hellocompose.data.login


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.hellocompose.ui.util.Result

class LoginMock : LoginRepo {
    override suspend fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        delay(100)
        emit(Result.Success(true))
    }

    override suspend fun login(model: String): Flow<Result<Int>> = flow {
        emit(Result.Loading)
        delay(1000)
        emit(Result.Success(4))
    }

}