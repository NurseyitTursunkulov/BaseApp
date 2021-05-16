package com.example.hellocompose.data.login


import android.util.Log
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.util.UserIsAlreadyRegistered
import com.example.hellocompose.ui.util.Result
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

    override suspend fun login(userAccount: UserAccount): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
//            emit(Result.Success("TokenResponse"))
            val freeCardResponse = apiService.getCardFree()
            val cardList = mutableListOf<Cards>()
            when {
                freeCardResponse.isSuccessful -> {
                    freeCardResponse.body()?.let { cards ->
                        val card = cards.first()
                        cardList.add(Cards(card.id,1))
//                        localDataSource.saveToken(model, token)
                    } ?: kotlin.run {
                        emit(Result.Error(Exception("Проблеммы с подключение интернета")))
                    }
                }
                else -> {
                    emit(Result.Error(Exception("Пользователь не найден")))
                }
            }
            userAccount.cards = cardList
            val response = apiService.login(userAccount)
            when {
                response.isSuccessful -> {
                    response.body()?.let { token ->
                        emit(Result.Success(token))
                        Log.d("Nurs","success")
//                        localDataSource.saveToken(model, token)
                    } ?: kotlin.run {
                        Log.d("Nurs","error")
                        emit(Result.Error(Exception("Проблеммы с подключение интернета")))
                    }
                }
                else -> {
                    Log.d("Nurs","rtad ${response.errorBody()}")
                    emit(Result.Error(UserIsAlreadyRegistered())) //todo change it later
//                    emit(Result.Error(Exception("Пользователь уже зарегистрирован")))
                }
            }
        } catch (e: Exception) {
            Log.d("Nurs","adfacce ${e.localizedMessage}")
            emit(Result.Error(Exception(e.localizedMessage)))
        }
    }
}