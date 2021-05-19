package com.example.hellocompose.data.login.remoteDS

import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.util.ExceptionInResponseBody
import com.example.hellocompose.data.util.ResponseIsNotSuccessfulException
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class RemoteDSImpl(val apiService: ApiService) : RemoteDS {
    override suspend fun registerAccount(userAccount: UserAccount): Result<String> {
        return apiService.registerAccount(userAccount).ifSuccessR()
    }

    override suspend fun getCardFree(): Result<List<Card>> {
        return apiService.getCardFree().ifSuccessR()

    }

    override suspend fun getLastCardNumber(): Result<Int> {
        return apiService.getLastCardNumber().ifSuccessR()
    }

    override suspend fun generateCards(lastCardNumber: CardPrint): Result<List<Card>> {
        return apiService.generateCards(lastCardNumber).ifSuccessR()
    }
}

suspend fun <T> Response<T>.ifSuccessR(onSuccess: suspend (T) -> Unit = {}): Result<T> {
    headers()
    return when {
        isSuccessful -> {
            body()?.let { cards ->
                onSuccess(cards)
                Result.Success(cards)
            } ?: kotlin.run {
                (Result.Error(ExceptionInResponseBody("Проблеммы с подключение интернета")))
            }
        }
        else -> {
            (Result.Error(ResponseIsNotSuccessfulException("Пользователь не найден")))
        }
    }

}