package com.example.hellocompose.data.login


import com.example.hellocompose.data.login.model.UserAccount
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.hellocompose.ui.util.Result

class LoginMock : LoginRepo {
    override suspend fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        delay(100)
        emit(Result.Success(false))
    }

    override suspend fun signIn(userAccount: UserAccount): Flow<Result<String>> = flow {
        emit(Result.Loading)
        delay(1000)
        emit(Result.Success("4"))
    }

    override suspend fun getToken(smsCode: String): Flow<Result<Unit>> {
        return flow {

        }
    }

    override suspend fun resendSMS(): Result<Unit> {
        TODO("Not yet implemented")
    }

}