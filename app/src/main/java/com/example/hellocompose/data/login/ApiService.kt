package com.example.hellocompose.data.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("BaseCrmApi/getLastCardNumber")
    suspend fun login(): Response<Int>
}