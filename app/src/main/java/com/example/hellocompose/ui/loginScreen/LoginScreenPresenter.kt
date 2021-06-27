package com.example.hellocompose.ui.loginScreen

interface LoginScreenPresenter {
   var loading: Boolean
   val showError: Pair<Boolean,String>
   val onErrorOkClick:()->Unit
   val onLoginClicl: (
    name: String,
    surname: String,
    phone: String,
    email: String,
    dateOfBirth: String
    ) -> Unit
}