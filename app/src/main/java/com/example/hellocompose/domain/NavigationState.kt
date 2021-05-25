package com.example.hellocompose.domain

sealed class LoginScreen<out R> {
    data class Error(val exception: Exception) : LoginScreen<Nothing>()
    object Loading : LoginScreen<Nothing>()
    data class NavigateToVerifyPhoneNumber(val phone: String) : LoginScreen<Nothing>()
}
sealed class VerifyPhoneNumberScreen<out R>{
    data class NavigateToMainScreen<out R>(val data: R) : VerifyPhoneNumberScreen<R>()
    data class ShowError(val exception: Exception) : VerifyPhoneNumberScreen<Nothing>()
    object ShowLoading : VerifyPhoneNumberScreen<Nothing>()

}