package com.example.hellocompose.data.login

import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.login.model.Card
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("BaseCrmApi/AccountRegister")
    suspend fun login(@Body userAccount: UserAccount): Response<String>
    @GET("BaseCrmApi/getCardFree")
    suspend fun getCardFree(): Response<List<Card>>

}