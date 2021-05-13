package com.example.hellocompose.data.login.localDataSource


interface LocalDataSource {
    suspend fun saveToken(model: String, token: String)
    suspend fun isUserSavedToLocalStorage(): Boolean
    suspend fun saveHotelIdent(ident:String)
    suspend fun getHotelIdent():String
}