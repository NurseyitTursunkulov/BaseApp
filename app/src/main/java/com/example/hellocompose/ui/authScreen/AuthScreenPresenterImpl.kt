package com.example.hellocompose.ui.authScreen

import com.example.hellocompose.ui.MainViewModel

class AuthScreenPresenterImpl(
    override var loading: Boolean,
    override val showError: Pair<Boolean, String>,
    override val sendNewSmsCodeButtonEnabled: Boolean,
    private val mainViewModel: MainViewModel
) : AuthScreenPresenter {
    override val countingFinished: () -> Unit
        get() = { mainViewModel.sendNewCodeEnabled.postValue(false) }
    override val enableNewSmsCodeButton: () -> Unit
        get() = {
            mainViewModel.sendNewCodeEnabled.postValue(true)
        }
    override val sendNewSmsCode: () -> Unit
        get() = {
            mainViewModel.sendNewSmsCode()
        }
    override val onSendButtonClick: (smsCode: String) -> Unit
        get() = { smsCode ->
            mainViewModel.getToken(smsCode)
        }
    override val errorSnackBarOnOkClick: () -> Unit
        get() = {
            mainViewModel.showError.postValue(Pair(false, ""))
        }
}