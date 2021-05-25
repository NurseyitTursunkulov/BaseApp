package com.example.hellocompose.ui

import com.example.hellocompose.data.login.localDataSource.LocalDataSourceImpl
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellocompose.domain.*
import com.example.hellocompose.ui.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    val entryPointUseCase: EntryPointUseCase,
    val local: LocalDataSourceImpl,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    val state = MutableLiveData<String>()
    val entryPointLiveData = MutableLiveData<Event<NavigationState>>()
    val showMainScreen = MutableLiveData<Boolean>()
    val showAuthScreen = MutableLiveData<Event<Boolean>>()

    init {
        Log.d("Nurs", "Viewmodel init")
        viewModelScope.launch {
            entryPointUseCase.invoke().collect {
                entryPointLiveData.postValue(Event(it))
            }
        }
        viewModelScope.launch {
            withContext(dispatcher) {
                local.isUserSavedToLocalStorage().collect {
                    Log.d("Nurs", "flow from datastore ${it}")
                }
            }
        }
    }

    fun makeSuspendCall() {
        viewModelScope.launch {
            if (showMainScreen.value == false) {
                showMainScreen.postValue(true)
            } else {
                showMainScreen.postValue(false)
            }

        }
    }

    val showLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Pair<Boolean,String>>()
    fun login(
        name: String,
        surname: String,
        phone: String,
        email: String,
        dateOfBirth: String
    ) {
        viewModelScope.launch {
            withContext(dispatcher) {
                loginUseCase.login(
                    name,
                    surname = surname,
                    phone,
                    email,
                    dateOfBirth
                ).collect {
                    when (it) {
                        is LoginScreen.Loading -> {
                            Log.d("Nurs", "loading true")
                            showLoading.postValue(true)
                        }
                        is LoginScreen.Error -> {
                            showLoading.postValue(false)
                            showError.postValue(Pair(true,it.exception.localizedMessage?:"error"))
                        }
                        is LoginScreen.NavigateToVerifyPhoneNumber -> {
                            showLoading.postValue(false)
                            entryPointLiveData.postValue(Event(NavigationState.NavigateToVerifyBySmsScreen))
                        }
                    }
                }
            }
        }
    }

    fun getToken(smsCode: String) {
        viewModelScope.launch {
            withContext(dispatcher){
                loginUseCase.getToken(smsCode).collect{
                    when (it) {
                        is VerifyPhoneNumberScreen.ShowLoading -> {
                            Log.d("Nurs", "loading true")
                            showLoading.postValue(true)
                        }
                        is VerifyPhoneNumberScreen.ShowError -> {
                            showLoading.postValue(false)
                            showError.postValue(Pair(true,it.exception.localizedMessage?:"error"))
                        }
                        is VerifyPhoneNumberScreen.NavigateToMainScreen -> {
                            showLoading.postValue(false)
                            entryPointLiveData.postValue(Event(NavigationState.NavigateToMainScreen))
                        }
                    }
                }
            }
        }
    }

}
