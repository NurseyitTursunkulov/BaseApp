package com.example.hellocompose.domain


import com.example.hellocompose.data.login.LoginRepo
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val loginRepo: LoginRepo
) {
    suspend fun login(
        name: String,
        surname:String,
        phone: String,
        email: String,
        dateOfBirth: String
    ): Flow<LoginScreen<String>> = flow {
        val userAccount = UserAccount(
            phoneNumber = phone,
            firstName = name,
            lastName = surname,
            email = email,
            birthday = dateOfBirth
        )
        loginRepo.signIn(userAccount).collect {
            when (it) {
                is Result.Success -> {
                    /** navigate to MainFragment**/
                    emit(LoginScreen.NavigateToMainScreen(it.data.toString()))
                }
                /** show loading**/
                is Result.Loading -> emit(LoginScreen.Loading)
                /** show error*/
                is Result.Error -> emit(LoginScreen.Error(it.exception))
            }
        }
    }
}

sealed class LoginScreen<out R> {
    data class NavigateToMainScreen<out R>(val data: R) : LoginScreen<R>()
    data class Error(val exception: Exception) : LoginScreen<Nothing>()
    object Loading : LoginScreen<Nothing>()
}