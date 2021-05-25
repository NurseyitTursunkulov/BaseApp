package com.example.hellocompose.data.login


import android.util.Log
import com.example.hellocompose.data.login.localDataSource.LocalDataSource
import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.login.remoteDS.RemoteDS
import com.example.hellocompose.data.util.ExceptionInResponseBody
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginRepoImpl(
    private val remoteDS: RemoteDS,
    private val localDataSource: LocalDataSource
) : LoginRepo {

    override suspend fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        emit(Result.Success(false))
//        localDataSource.isUserSavedToLocalStorage().collect {
//            if (it.userId.isEmpty()){
//                emit(Result.Success(false))
//            }
//            else{
//                emit(Result.Success(true))
//            }
//        }
    }

    /**
    1 Получить ID свободной карты
    GET /api​/BaseCrmApi​/getCardFree
    Если массив пришёл пустым необходима сгенерировать карты!!!
    GET/ api​/BaseCrmApi​/getLastCardNumber (узнаём номер последней сгенерированной карты)
    POST /api​/BaseCrmApi​/cardPrint
    "fromPrint": номер последней сгенерированной карты + 1,
    "toPrint": "fromPrint" + количество генерируемых карт

    2 Нужно зарегистрировать его!!!
    POST /api​/BaseCrmApi​/AccountRegister
    (cardId: Свободная карта)
    ("cardType": 1)

    3 Получить пароль по смс
    POST /api​/BaseCrmApi​/sendSmsWithCode

    4 Получить токен на 5 минут
    PUT /api​/BaseCrmApi​/getToken
    "clientName": "Номер телефона",
    "password": "код отправленный в СМС " */
    override suspend fun signIn(userAccount: UserAccount): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val cardList = mutableListOf<Cards>()
            onSuccessResponse<String, List<Card>>(remoteDS.getCardFree()) { freeCardList ->
                freeCardList.firstOrNull()?.let { card ->
                    cardList.add(Cards(card.id, 1))
                }

                if (cardList.isEmpty()) {
                    onSuccessResponse<String, Int>(remoteDS.getLastCardNumber()) { lastCardNumber ->
                        onSuccessResponse<String, List<Card>>(
                            remoteDS.generateCards(
                                CardPrint(
                                    lastCardNumber + 1,
                                    lastCardNumber + 10
                                )
                            )
                        ) { generatedCards ->
                            if (generatedCards.isEmpty()) {
                                emit(Result.Error(ExceptionInResponseBody("returned empty cards")))
                                return@flow
                            }
                            cardList.add(Cards(generatedCards.first().id, 1))
                        }
                    }
                }
            }
            if (cardList.isNotEmpty()) {
                val response = remoteDS.registerAccount(userAccount)
                when (response) {
                    is Result.Success -> {
                        userAccount.userId = response.data
                        localDataSource.saveUser(userAccount)
                        remoteDS.sendSmsCode(userAccount.phoneNumber)
                    }
                    else -> {
                    }
                }

                emit(response)
            }
        } catch (e: Exception) {
            Log.d("Nurs", "adfacce ${e.localizedMessage}")
            emit(Result.Error(Exception(e.localizedMessage)))
        }
    }

    override suspend fun getToken(smsCode: String): Flow<Result<Unit>> {
        val phoneNumber = localDataSource.getPhoneNumber()
        return flow {
            val response = remoteDS.getToken(phoneNumber, smsCode)
            when (response) {
                is Result.Success -> {
                    localDataSource.saveToken(response.data)
                    emit(Result.Success(Unit))
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
                Result.Loading -> {
                    emit(Result.Loading)
                }
            }
        }
    }

    suspend inline fun <T, D> FlowCollector<Result<T>>.onSuccessResponse(
        r: Result<D>,
        onSuccess: (D) -> Unit
    ) {
        when (r) {
            is Result.Success -> {
                onSuccess(r.data)
            }
            is Result.Error -> {
                emit(Result.Error(r.exception))
            }
            Result.Loading -> {
                emit(Result.Loading)
            }
        }
    }
}