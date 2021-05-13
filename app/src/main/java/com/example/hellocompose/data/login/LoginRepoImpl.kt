package com.example.hellocompose.data.login


import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepoImpl(
    private val apiService: ApiService,
//    private val localDataSource: LocalDataSource
) : LoginRepo {

    override suspend fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
//        emit(Result.Loading)
//        if (localDataSource.isUserSavedToLocalStorage()){
//            emit(Result.Success(true))
//        }else{
//            emit(Result.Success(false))
//        }
        emit(Result.Loading)
//        delay(3000)
        emit(Result.Success(false))
    }

    override suspend fun login(model: String): Flow<Result<Int>> = flow {
        try {
//            emit(Result.Success("TokenResponse"))
            val response = apiService.login()
            when {
                response.isSuccessful -> {
                    response.body()?.let { token ->
                        emit(Result.Success(token))
//                        localDataSource.saveToken(model, token)
                    } ?: kotlin.run {
                        emit(Result.Error(Exception("Проблеммы с подключение интернета")))
                    }
                }
                else -> {
                    emit(Result.Error(Exception("Пользователь не найден")))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(Exception("Проблеммы с подключение интернета")))
        }
    }
}