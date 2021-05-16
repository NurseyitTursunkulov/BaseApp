package com.example.hellocompose.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellocompose.domain.EntryPointUseCase
import com.example.hellocompose.domain.LoginScreen
import com.example.hellocompose.domain.LoginUseCase
import com.example.hellocompose.domain.NavigationState
import com.example.hellocompose.ui.util.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    val entryPointUseCase: EntryPointUseCase
) : ViewModel() {
    val state = MutableLiveData<String>()
    val entryPointLiveData = MutableLiveData<Event<NavigationState>>()
    val showMainScreen = MutableLiveData<Boolean>()

    init {
        Log.d("Nurs","Viewmodel init")
        viewModelScope.launch {
            entryPointUseCase.invoke().collect {
                entryPointLiveData.postValue(Event(it))
            }
        }
    }

    fun makeSuspendCall() {
        viewModelScope.launch {
            if(showMainScreen.value == false){
                showMainScreen.postValue(true)
            }else{
                showMainScreen.postValue(false)
            }

//            loginUseCase.login("dfd").collect {
//                when (it) {
//                    is LoginScreen.NavigateToMainScreen -> {
//                        state.postValue("fefe ${(0..100).random()}")
//                    }
//                    else -> {
//
//                    }
//                }
//            }

        }
    }

    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    fun login(
        name: String,
        surname:String,
        phone: String,
        email: String,
        dateOfBirth: String
    ) {
        viewModelScope.launch {
            loginUseCase.login(
                name,
                surname = surname,
                phone,
                email,
                dateOfBirth
            ).collect {
                when (it) {
                    is LoginScreen.NavigateToMainScreen -> {
                        state.postValue("fefe ${(0..100).random()}")
                        showLoading.postValue(false)
                        entryPointLiveData.postValue(Event(NavigationState.NavigateToMainScreen))
                    }
                    is LoginScreen.Loading ->{
                        Log.d("Nurs","loading true")
                        showLoading.postValue(true)
                    }
                    is LoginScreen.Error ->{
                        showLoading.postValue(false)
                        showError.postValue(it.exception.localizedMessage)
                    }
                }
            }

        }
    }

}
