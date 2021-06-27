package com.example.hellocompose.ui.authScreen

interface AuthScreenPresenter {
    var loading: Boolean
    val showError: Pair<Boolean,String>
    val sendNewSmsCodeButtonEnabled: Boolean
    val countingFinished: () -> Unit
    val enableNewSmsCodeButton :  () -> Unit
    val sendNewSmsCode: () -> Unit
    val onSendButtonClick: (smsCode: String) -> Unit
    val errorSnackBarOnOkClick: () -> Unit
}