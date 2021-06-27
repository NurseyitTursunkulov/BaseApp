package com.example.hellocompose.ui.loginScreen

import com.example.hellocompose.ui.MainViewModel

class LoginScreenPresenterImpl(
    override var loading: Boolean,
    override val showError: Pair<Boolean, String>,
    val viewModel: MainViewModel
) : LoginScreenPresenter {
    override val onErrorOkClick: () -> Unit
        get() = {
            viewModel.showError.postValue(
                Pair(false, "")
            )
        }
    override val onLoginClicl: (name: String, surname: String, phone: String, email: String, dateOfBirth: String) -> Unit
        get() = { name, surName, phoneNumber, email, birthDate ->
            viewModel.login(
                name,
                surName,
                phoneNumber,
                email,
                birthDate
            )
        }

}