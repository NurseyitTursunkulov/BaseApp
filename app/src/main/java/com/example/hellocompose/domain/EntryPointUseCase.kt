package com.example.hellocompose.domain

import com.example.hellocompose.data.login.LoginRepo
import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/** entry point of application, in this part we navigate user according his auth status**/
class EntryPointUseCase(private val loginRepo: LoginRepo) {
    suspend operator fun invoke(): Flow<NavigationState> = flow {
        loginRepo.isUserLoggedIn().collect {
            when (it) {
                is Result.Success -> {
                    if (it.data) {
                        emit(NavigationState.NavigateToMainScreen)
                    } else {
                        emit(NavigationState.NavigateToVerifyBySmsScreen)
                    }
                }
                is Result.Loading -> emit(NavigationState.ShowLoading)
                is Result.Error -> emit(NavigationState.ShowError(it.exception))
            }
        }
    }
}

sealed class NavigationState {
    data class ShowError(val exception: Exception) : NavigationState()
    object ShowLoading : NavigationState()
    object NavigateToMainScreen : NavigationState()
    object NavigateToLoginScreen : NavigationState()
    object NavigateToVerifyBySmsScreen : NavigationState()
}