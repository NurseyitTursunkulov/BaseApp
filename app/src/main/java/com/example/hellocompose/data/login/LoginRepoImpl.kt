package com.example.hellocompose.data.login


import android.util.Log
import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.login.remoteDS.RemoteDS
import com.example.hellocompose.data.util.ExceptionInResponseBody
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class LoginRepoImpl(
    private val remoteDS: RemoteDS,
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
                userAccount.cards = cardList
                val response = remoteDS.registerAccount(userAccount)
                emit(response)
            }
        } catch (e: Exception) {
            Log.d("Nurs", "adfacce ${e.localizedMessage}")
            emit(Result.Error(Exception(e.localizedMessage)))
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