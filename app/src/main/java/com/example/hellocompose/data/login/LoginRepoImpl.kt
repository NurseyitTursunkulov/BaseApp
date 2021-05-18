package com.example.hellocompose.data.login


import android.util.Log
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.util.UserIsAlreadyRegistered
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

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

    override suspend fun signIn(userAccount: UserAccount): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val cardList = mutableListOf<Cards>()
            apiService.getCardFree().ifSuccess {cards->
                val card = cards.first()
                cardList.add(Cards(card.id, 1))
            }
            if (cardList.isEmpty()) {
                apiService.getLastCardNumber().ifSuccess { lastCardNumber ->
                    apiService.generateCards(
                        CardPrint(
                            lastCardNumber + 1,
                            lastCardNumber + 10
                        )
                    ).ifSuccess {
                        val card = it.first()
                        cardList.add(Cards(card.id, 1))
                    }
                }
            }

            userAccount.cards = cardList

            val response = apiService.registerAccount(userAccount)
            when {
                response.isSuccessful -> {
                    response.body()?.let { token ->
                        emit(Result.Success(token))
//                        localDataSource.saveToken(model, token)
                    } ?: kotlin.run {
                        Log.d("Nurs", "error")
                        emit(Result.Error(Exception("Проблеммы с подключение интернета")))
                    }
                }
                else -> {
                    Log.d("Nurs", "rtad ${response.errorBody()}")
                    emit(Result.Error(UserIsAlreadyRegistered())) //todo change it later
                }
            }

        } catch (e: Exception) {
            Log.d("Nurs", "adfacce ${e.localizedMessage}")
            emit(Result.Error(Exception(e.localizedMessage)))
        }
    }
}


fun <T> Response<T>.ifSuccess(onSuccess: suspend (T) -> Unit): Flow<Result<T>> {
    return flow {
        when {
            isSuccessful -> {
                body()?.let { cards ->
                    onSuccess(cards)
                } ?: kotlin.run {
                    emit(Result.Error(Exception("Проблеммы с подключение интернета")))
                }
            }
            else -> {
                emit(Result.Error(Exception("Пользователь не найден")))
            }
        }
    }
}