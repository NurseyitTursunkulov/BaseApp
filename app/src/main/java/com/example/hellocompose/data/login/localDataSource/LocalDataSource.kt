package com.example.hellocompose.data.login.localDataSource

import com.example.hellocompose.data.login.model.Token
import com.example.hellocompose.data.login.model.UserAccount
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    suspend fun isUserSavedToLocalStorage(): Flow<UserAccount>
    suspend fun saveUser (model: UserAccount)
    suspend fun getPhoneNumber(): String
    suspend fun saveToken(data: Token)
}