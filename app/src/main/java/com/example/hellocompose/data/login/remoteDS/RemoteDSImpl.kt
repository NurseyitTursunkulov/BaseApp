package com.example.hellocompose.data.login.remoteDS

import android.util.Log
import com.example.hellocompose.data.login.model.*
import com.example.hellocompose.data.util.ExceptionInResponseBody
import com.example.hellocompose.data.util.ResponseIsNotSuccessfulException
import com.example.hellocompose.ui.util.Result
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

    override suspend fun sendSmsCode(phoneNumber: String): Result<Unit> {
        return apiService.sendSms(phoneNumber).ifSuccessR()
    }

    override suspend fun getToken(phoneNumber: String, smsCode: String): Result<Token> {
        val tokenRes = apiService.getToken(TokenRequest(clientName = phoneNumber, password = smsCode))

        if (tokenRes.isSuccessful) {
            tokenRes.body()?.let { cards ->
                Log.d("Nurs","token success ${cards}")
                return Result.Success(cards)
            } ?: kotlin.run {
                Log.d("Nurs","token error ${tokenRes}")
                return (Result.Error(ExceptionInResponseBody("Проблеммы с подключение интернета")))
            }
        } else {
            Log.d("Nurs","token error ${tokenRes.errorBody()}")
            return (Result.Error(ResponseIsNotSuccessfulException("Пользователь не найден")))
        }

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