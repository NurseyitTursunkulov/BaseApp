package com.example.hellocompose.data.login.remoteDS

import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("BaseCrmApi/AccountRegister")
    suspend fun registerAccount(@Body userAccount: UserAccount): Response<String>
    @GET("BaseCrmApi/getCardFree")
    suspend fun getCardFree(): Response<List<Card>>
    @GET("BaseCrmApi/getLastCardNumber")
    suspend fun getLastCardNumber(): Response<Int>
    @POST("BaseCrmApi/cardPrint")
    suspend fun generateCards(lastCardNumber: CardPrint):Response<List<Card>>

}