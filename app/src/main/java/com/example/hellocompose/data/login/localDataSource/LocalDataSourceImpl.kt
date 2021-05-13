//package com.timelysoft.shelter.data.login.localDataSource
//
//import com.example.hellocompose.data.login.localDataSource.LocalDataSource
//import com.timelysoft.shelter.android.extension.timeToLong
//import com.timelysoft.shelter.android.service.AppPreferences
//import com.timelysoft.shelter.android.service.request.AuthModel
//import com.timelysoft.shelter.android.service.response.TokenResponse
//
//class LocalDataSourceImpl : LocalDataSource {
//    override suspend fun saveToken(model: AuthModel, token: TokenResponse) {
//        AppPreferences.isLogined = true
//        AppPreferences.login = model.userName
//        AppPreferences.token = token.accessToken
//        AppPreferences.refreshToken = token.refreshToken
//        AppPreferences.tokenRefreshTime = token.expiresUtc.timeToLong()
//        AppPreferences.fullName = token.fullName
//    }
//
//    override suspend fun isUserSavedToLocalStorage(): Boolean {
//        with(AppPreferences) {
//            listOf<String?>(fullName,refreshToken,token,login).forEach {
//                if (it == null || it.isEmpty()) return false
//            }
//            return true
//        }
//    }
//
//    override suspend fun saveHotelIdent(ident: String) {
//        AppPreferences.hotelIdent = ident
//    }
//
//    override suspend fun getHotelIdent(): String {
//        return AppPreferences.hotelIdent!!
//    }
//}