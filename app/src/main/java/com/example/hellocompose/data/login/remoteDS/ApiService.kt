package com.example.hellocompose.data.login.remoteDS

import com.example.hellocompose.data.login.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("BaseCrmApi/AccountRegister")
    suspend fun registerAccount(@Body userAccount: UserAccount): Response<String>

    @GET("BaseCrmApi/getCardFree")
    suspend fun getCardFree(): Response<List<Card>>

    @GET("BaseCrmApi/getLastCardNumber")
    suspend fun getLastCardNumber(): Response<Int>

    @POST("BaseCrmApi/cardPrint")
    suspend fun generateCards(lastCardNumber: CardPrint): Response<List<Card>>

    @POST("BaseCrmApi/sendSmsWithCode")
    suspend fun sendSms(
        @Query("phoneNumber") phoneNumber: String
    ): Response<Unit>
    @Headers("Content-Type: application/json")
    @PUT("BaseCrmApi/getToken")
    suspend fun getToken(@Body tokenRequest: TokenRequest):Response<Token>

}