package com.example.hellocompose.data.login.remoteDS

import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.Token
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.domain.LoginScreen
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDS {
    suspend fun registerAccount(userAccount: UserAccount): Result<String>
    suspend fun getCardFree(): Result<List<Card>>
    suspend fun getLastCardNumber(): Result<Int>
    suspend fun generateCards(lastCardNumber: CardPrint): Result<List<Card>>
    suspend fun sendSmsCode(phoneNumber:String):Result<Unit>
    suspend fun getToken(phoneNumber: String,smsCode: String):Result<Token>
}